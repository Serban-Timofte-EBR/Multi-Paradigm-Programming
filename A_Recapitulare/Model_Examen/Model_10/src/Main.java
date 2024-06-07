import netscape.javascript.JSObject;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Proiect> citireProiecteJSON() {
        List<Proiect> lista = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("proiecte.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int cod = obj.getInt("cod_proiect");
                String nume = obj.getString("nume");
                String descriere = obj.getString("descriere");
                String manager = obj.getString("manager");
                Double buget = obj.getDouble("buget");

                Proiect proiect = new Proiect(cod, nume, descriere, manager, buget);
                lista.add(proiect);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Cost> citireCosturiXML() {
        List<Cost> lista = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("costuri.xml"));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            NodeList nodeList = doc.getElementsByTagName("cost");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);

                int codCost = Integer.parseInt(elem.getElementsByTagName("cod_cost").item(0).getTextContent());
                int codProiect = Integer.parseInt(elem.getElementsByTagName("cod_proiect").item(0).getTextContent());
                String denumire = elem.getElementsByTagName("denumire_cost").item(0).getTextContent();
                String unitate = elem.getElementsByTagName("um").item(0).getTextContent();
                Double cantitate = Double.parseDouble(elem.getElementsByTagName("cantitate").item(0).getTextContent());
                Double pretUnitar = Double.parseDouble(elem.getElementsByTagName("pu").item(0).getTextContent());

                Cost cost = new Cost(codCost, codProiect, denumire, unitate, cantitate, pretUnitar);
                lista.add(cost);
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

    public static void scriereXML(Map<Integer, Double> coduri_valori) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("costuri");
            doc.appendChild(root);

            for(Map.Entry<Integer, Double> entry : coduri_valori.entrySet()) {
                Element elem = doc.createElement("cost_rezumat");
                elem.setAttribute("cod_proiect", String.valueOf(entry.getKey()));
                elem.setAttribute("valoare_totala", String.valueOf(entry.getValue()));

                root.appendChild(elem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("proiect_valoare.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Proiect> proiecte = citireProiecteJSON();
        List<Cost> costuri = citireCosturiXML();

        // proiecte.forEach(System.out::println);
        // costuri.forEach(System.out::println);

        Map<String, Double> manageri_buget = proiecte.stream().collect(
                Collectors.groupingBy(
                        Proiect::getManager,
                        Collectors.summingDouble(Proiect::getBuget)
                ));

        System.out.println("CERINTA 1: Bugetele managerilor");
        for(Map.Entry<String, Double> entry : manageri_buget.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        Map<Integer, Double> coduri_valori = costuri.stream()
                .collect(
                        Collectors.groupingBy(
                                Cost::getCodCost,
                                Collectors.summingDouble(cost -> cost.getPretUnitate() * cost.getCantitate())
                        ));

        System.out.println("CERINTA 2:");
        for(Map.Entry<Integer, Double> entry : coduri_valori.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println();

        System.out.println("CERINTA 3:");
        scriereXML(coduri_valori);
        System.out.println();
    }
}