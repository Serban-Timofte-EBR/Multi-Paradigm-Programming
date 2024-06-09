import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Produs> citireProduseJSON() {
        List<Produs> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("produse.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int id = object.getInt("id");
                String denumire = object.getString("denumire");
                String categorie = object.getString("categorie");
                Double pret = object.getDouble("pret");

                Produs produs = new Produs(id,denumire,categorie,pret);
                lista.add(produs);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Vanzare> citireVanzariXML() {
        List<Vanzare> lista = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("vanzari.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("vanzare");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                int idProdus = Integer.parseInt(element.getElementsByTagName("id_produs").item(0).getTextContent());
                int cantitatea = Integer.parseInt(element.getElementsByTagName("cantitate").item(0).getTextContent());
                String data = element.getElementsByTagName("data").item(0).getTextContent();

                Vanzare vanzare = new Vanzare(id, idProdus, cantitatea, data);
                lista.add(vanzare);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Produs> produse = citireProduseJSON();
        List<Vanzare> vanzari = citireVanzariXML();

        Map<String, Double> categorie_pretMediu = produse.stream()
                .collect(Collectors.groupingBy(
                        Produs::getCategorie,
                        Collectors.averagingDouble(Produs::getPret)
                ));

        // Să se afișeze la consolă lista produselor și prețul mediu al acestora pe categorii
        System.out.println("CERINTA 1 - Pretul mediu a categoriilor:");
        DecimalFormat format = new DecimalFormat("#.00");
        categorie_pretMediu.entrySet().forEach(stringDoubleEntry ->
                System.out.println(stringDoubleEntry.getKey() + " - " + format.format(stringDoubleEntry.getValue())));
        System.out.println();

        // Să se afișeze la consolă cantitatea totală de produse vândute pe fiecare categorie, ordonate descrescător după cantitate
        Map<String, Integer> cerinta2 = vanzari.stream()
                .collect(Collectors.groupingBy(
                        vanzare -> produse.stream()
                                .filter(produs -> produs.getId() == vanzare.getIdProdus())
                                .findFirst().get().getCategorie(),
                        Collectors.summingInt(Vanzare::getCantitate)
                )).entrySet().stream()
                        .sorted((e1,e2) -> Double.compare(e2.getValue(), e1.getValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1,e2) -> e2,
                                        LinkedHashMap::new
                                ));
        System.out.println("CERINTA 2 - Categoria si numarul de produse");
        cerinta2.entrySet().forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey() + " - " + stringIntegerEntry.getValue()));
        System.out.println();

//        Să se salveze într-un fișier raport_vanzari.txt informațiile despre vânzări, grupate pe produse, astfel:
//        Laptop
//        Data: 2023-01-10, Cantitate: 2
//        Data: 2023-01-12, Cantitate: 3
//
//        Telefon
//        Data: 2023-01-14, Cantitate: 1

        System.out.println("CERINTA 3 - Raportul de vanzari:");
        for(Produs produs : produse) {
            List<Vanzare> vanzariProdus = vanzari.stream()
                    .filter(vanzare -> vanzare.getIdProdus() == produs.getId())
                    .toList();

            if(vanzariProdus.size() != 0) {
                System.out.println(produs.getDenumire());
                for (Vanzare vanzare : vanzariProdus) {
                    System.out.println("Data: " + vanzare.getData() + ", " + "Cantitate: " + vanzare.getCantitate());
                }
            }
            System.out.println();
        }

        // scriere in txt
        try (FileOutputStream fos = new FileOutputStream("raport.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            for(Produs produs : produse) {
                List<Vanzare> vanzariProdus = vanzari.stream()
                        .filter(vanzare -> vanzare.getIdProdus() == produs.getId())
                        .toList();

                if(vanzariProdus.size() != 0) {
                    bw.write(produs.getDenumire() + System.lineSeparator());
                    for (Vanzare vanzare : vanzariProdus) {
                        String line = "Data: " + vanzare.getData() + ", " + "Cantitate: " + vanzare.getCantitate() + System.lineSeparator();
                        bw.write(line);
                    }
                }
                bw.write(System.lineSeparator());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // scriere in JSON
        JSONArray jsonArray = new JSONArray();

        for(Produs produs : produse) {
            List<Vanzare> vanzariProdus = vanzari.stream()
                    .filter(vanzare -> vanzare.getIdProdus() == produs.getId())
                    .toList();

            if(vanzariProdus.size() != 0) {
                JSONObject object = new JSONObject();
                object.put("Produs", produs.getDenumire());

                JSONArray vanzProd = new JSONArray();
                for (Vanzare vanzare : vanzariProdus) {
                    JSONObject vanz = new JSONObject();
                    vanz.put("Data", vanzare.getData());
                    vanz.put("Cantitate", vanzare.getCantitate());

                    vanzProd.put(vanz);
                }
                object.put("Vanzari", vanzProd);

                jsonArray.put(object);
                }
            }

        try (FileWriter fw = new FileWriter("raport.json")) {
            fw.write(jsonArray.toString(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Să se afișeze la consolă lista produselor care au fost vândute într-o cantitate totală mai mare de 2 bucăți.
        Map<Integer, Integer> idProdus_cantittea = vanzari.stream()
                .collect(Collectors.groupingBy(
                        Vanzare::getIdProdus,
                        Collectors.summingInt(Vanzare::getCantitate)
                ));

        Map<String, Integer> produs_cantitatea = new HashMap<>();

        for(Map.Entry<Integer, Integer> entry : idProdus_cantittea.entrySet()) {
            String denumire = produse.stream()
                    .filter(produs -> produs.getId() == entry.getKey())
                    .findFirst().get().getDenumire();

            produs_cantitatea.put(denumire, entry.getValue());
        }

        System.out.println("CERINTA 4 - Cantitatile de produse vandute");
        produs_cantitatea.entrySet().forEach(stringIntegerEntry -> System.out.println(
                stringIntegerEntry.getKey() + " - " + stringIntegerEntry.getValue()
        ));
        System.out.println();

        System.out.println("CERINTA 5 - Lista produselor dupa pret");
        produse.stream().sorted((p1, p2) -> Double.compare(p2.getPret(), p1.getPret()))
                .forEach(produs -> System.out.println(produs.getDenumire() + " - " + produs.getPret()));
        System.out.println();

        // server TCP/IP
            // Clientul trimite id ul de produs
            // Serverul raspunde cu lista de vanzari

        int port = 12345;

        try (ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul s-a pornit!");
            while (true) {
                Socket client = server.accept();
                new Thread(new ServerRequestsHandler(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}