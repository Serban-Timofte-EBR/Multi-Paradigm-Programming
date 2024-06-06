import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Carte> citireCartiXML() {
        List<Carte> lista = new ArrayList<>();

        try {
           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = dbf.newDocumentBuilder();
           Document doc = db.parse(new File("biblioteca.xml"));
           doc.getDocumentElement().normalize();

           Element root = doc.getDocumentElement();

           NodeList nodeList = root.getElementsByTagName("carte");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);

                String titlu = elem.getElementsByTagName("titlu").item(0).getTextContent();
                String autor = elem.getElementsByTagName("autor").item(0).getTextContent();
                String gen = elem.getElementsByTagName("gen").item(0).getTextContent();
                int nrPagini = Integer.parseInt(elem.getElementsByTagName("numarPagini").item(0).getTextContent());

                Carte carte = new Carte(titlu, autor,gen, nrPagini);
                lista.add(carte);
            }
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Cititor> citireCititoriJSON() {
        List<Cititor> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("cititori.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jstok);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String nume = obj.getString("nume");

                List<CarteImprumutata> cartiImprumutate = new ArrayList<>();
                JSONArray cartiJSON = obj.getJSONArray("cartiImprumutate");
                for (int j = 0; j < cartiJSON.length(); j++) {
                    JSONObject carte = cartiJSON.getJSONObject(j);
                    String titlu = carte.getString("titlu");
                    String data = carte.getString("dataImprumut");

                    CarteImprumutata carteImpr = new CarteImprumutata(titlu, data);
                    cartiImprumutate.add(carteImpr);
                }

                Cititor cititor = new Cititor(nume, cartiImprumutate);
                lista.add(cititor);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Cititor> cititori = citireCititoriJSON();
        List<Carte> carti = citireCartiXML();

//        cititori.forEach(System.out::println);
//        carti.forEach(System.out::println);

        // Cerinta 1 - Numarul total de pagini din fiecare gen
        Map<String, Integer> pagini_per_gen = carti.stream().collect(
                Collectors.groupingBy(
                        Carte::getGen,
                        Collectors.summingInt(Carte::getNumarPagini)
                ));
        Map<String, Integer> sortare1 = (Map<String, Integer>) pagini_per_gen.entrySet()
                        .stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));

        System.out.println("CERINTA 1: Numarul de pagini:");
        for(Map.Entry<String, Integer> entry : sortare1.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " pagini");
        }
        System.out.println();

        List<Cititor> cititoriCuMin2Carti = cititori.stream()
                .filter(cititor -> cititor.getCartiImprumutate().size() > 1).sorted((c1, c2) -> c1.getNume().compareTo(c2.getNume())).toList();
        System.out.println("CERINTA 2: Cititorii cu cel putin 2 carti");
        for(Cititor cititor : cititoriCuMin2Carti) {
            System.out.println(cititor.getNume() + " - " + cititor.getCartiImprumutate().size() + " carti citite");
        }

        // Scriem lista cu carti in json
        List<Carte> cartiSortate = carti.stream().sorted((carte1, carte2) -> carte1.getAutor().compareTo(carte2.getAutor())).toList();

        JSONArray jsonArray = new JSONArray();

        for(Carte carte : cartiSortate) {
            JSONObject obj = new JSONObject();
            obj.put("Autor", carte.getAutor());
            obj.put("Titlu", carte.getTitlu());
            obj.put("Gen", carte.getGen());
            obj.put("Numar Pagini", carte.getNumarPagini());

            jsonArray.put(obj);
        }

        try (FileWriter fw = new FileWriter("carti.json")) {
            fw.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Scriem lista de cititoti intr-un fisier XML;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("carti");
            doc.appendChild(root);

            for(Carte carte : carti) {
                Element elem = doc.createElement("carte");
                elem.setAttribute("Titlu", carte.getTitlu());
                elem.setAttribute("Autor", carte.getAutor());
                elem.setAttribute("Gen", carte.getGen());
                elem.setAttribute("NumarPagini", String.valueOf(carte.getNumarPagini()));
                root.appendChild(elem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("carte.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        // Tabela cu carti si de cate ori a fost imprumutata si scrierea intr-o tabela SQL

        String url = "jdbc:sqlite:biblioteca.db";
        String query = "CREATE TABLE IF NOT EXISTS Carti2 (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nume TEXT, " +
                "autor TEXT, " +
                "gen TEXT, " +
                "nrPagini INTERGER, " +
                "nrImprumuturi INTERGER)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement())
        {
            stm.execute(query);
            for(Carte carte : carti) {
                int counter = 0;

                for(Cititor cititor : cititori) {
                    for(CarteImprumutata carteImprumutata : cititor.getCartiImprumutate()) {
                        if(carteImprumutata.getTitlu().equals(carte.getTitlu())) {
                            counter++;
                        }
                    }
                }

                String query2 = "INSERT INTO Carti2 (nume, autor, gen, nrPagini, nrImprumuturi) VALUES (" +
                        "'" + carte.getTitlu() + "', " +
                        "'" + carte.getAutor() + "', " +
                        "'" + carte.getGen() + "', " +
                        carte.getNumarPagini() + ", " + counter + ")";

                stm.execute(query2);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}