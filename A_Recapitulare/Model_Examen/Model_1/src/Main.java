import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

public class Main {
    public static void main(String[] args) throws IOException {
        // --------- Cerinta 1 - Valoarea totala a materiilor prime din fisierul .db ---------------
        String url = "jdbc:sqlite:examen.db";
        String query = "SELECT SUM(Cantitate * Pret_unitar) AS valTot FROM MateriiPrime";

        try(Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query)) {
                double valTotala = res.getDouble("valTot");
                System.out.println("-->Cerinta 1:");
                System.out.println(valTotala);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ------ Extra: Citirea listei de materii prime din examen.db ---------
        List<MateriePrima> materiiPrime = new ArrayList<>();

        String url2 = "jdbc:sqlite:examen.db";
        String query2 = "SELECT * FROM MateriiPrime";

        try(Connection conn = DriverManager.getConnection(url2);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query2)) {
                while (res.next()) {
                    int cod = res.getInt("Cod");
                    String denumire = res.getString("Denumire");
                    double cantitate = res.getDouble("Cantitate");
                    double pretUnitar = res.getDouble("Pret_unitar");
                    String unitateMasura = res.getString("Unitate_masura");

                    MateriePrima mat = new MateriePrima(cod, denumire, cantitate, pretUnitar, unitateMasura);

                    materiiPrime.add(mat);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Materiile prime din fisier sunt:");
        materiiPrime.forEach(System.out::println);

        // ------ Extra: Citirea listei de produse din produse.json ---------
        List<Produs> produseFisier = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Produse.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {

                JSONTokener jstok = new JSONTokener(br);
                JSONArray jsar = new JSONArray(jstok);

            for (int i = 0; i < jsar.length(); i++) {
                JSONObject obj = jsar.getJSONObject(i);

                int codProdus = obj.getInt("Cod produs");
                String denumire = obj.getString("Denumire produs");

                Map<Integer, Double> consumuri = new HashMap<>();

                JSONArray consumuriArr = obj.getJSONArray("Consumuri");
                for (int j = 0; j < consumuriArr.length(); j++) {
                    JSONObject consum = consumuriArr.getJSONObject(j);

                    int codMateriePrima = consum.getInt("Cod materie prima");
                    double cantitate = consum.getDouble("Cantitate");

                    double cantitateFin = consumuri.getOrDefault(codMateriePrima, 0.0) + cantitate;

                    consumuri.put(codMateriePrima, cantitateFin);
                }

                int cantitate = obj.getInt("Cantitate");
                String unitateMasura = obj.getString("Unitate masura");

                Produs prod = new Produs(codProdus, denumire, consumuri, cantitate, unitateMasura);

                produseFisier.add(prod);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ---------------- Extra 4: Scierea listei de materii prime intr-un JSON file ------------------------
        JSONArray jsonArray = new JSONArray();

        for(MateriePrima materie : materiiPrime) {
            JSONObject obj = new JSONObject();
            obj.put("Cod", materie.getCod());
            obj.put("Denumire", materie.getDenumire());
            obj.put("Cantitate", materie.getCantitate());
            obj.put("Pret Unitar", materie.getPretUnitar());
            obj.put("Unitate de masura", materie.getUnitateMasura());
            jsonArray.put(obj);
        }

        try(FileWriter fw = new FileWriter("MateriiPrime.json")) {
            fw.write(jsonArray.toString(4));
        }

        System.out.println("Produsele din fisier:");
        produseFisier.forEach(System.out::println);

        // ---------------- Cerinta 2: Să se afișeze la consolă produsele de panificatie sortate descrescator dupa numarul de materii prime utilizate in fabricatie. --------------------
        List<Produs> produseDescrescator = produseFisier.stream().sorted(
                Comparator.comparingInt( (Produs prod) -> prod.getConsumuri().size()).reversed()).toList();

        System.out.println("-->Cerinta 2:");
        produseDescrescator.forEach(System.out::println);


        // --------------- Cerinta 3: Actualizare cantitatilor materilor prime si printarea intr-un fisier XML -----------------------------------

            // extrag consumurile separat
        Map<Integer, Double> consumiriSep = new HashMap<>();

        for(Produs prod : produseFisier) {
            for(Map.Entry<Integer, Double> consum : prod.getConsumuri().entrySet()) {
                int cod = consum.getKey();
                double val = consum.getValue();

                double consumTotal = consumiriSep.getOrDefault(cod, 0.0) + val;

                consumiriSep.put(cod, consumTotal);
            }
        }

            // actualizam in lista de materii prime

        for (MateriePrima mat : materiiPrime) {
            for(Map.Entry<Integer, Double> consum : consumiriSep.entrySet()) {
                int cod = consum.getKey();
                double cant = consum.getValue();
                if(mat.getCod() == cod) {
                    double cantAct = mat.getCantitate();
                    mat.setCantitate(cantAct - cant);
                }
            }
        }

            // scriem in XML
        try {
            // Crearea unui document XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Setarea radacinii
            Element root = doc.createElement("materii_prime");
            doc.appendChild(root);

            // Popularea documentului XML
            for(MateriePrima materiePrima : materiiPrime) {
                Element elemMaterie = doc.createElement("materie_prima");
                elemMaterie.setAttribute("cod", String.valueOf(materiePrima.getCod()));
                elemMaterie.setAttribute("denumire", materiePrima.getDenumire());
                elemMaterie.setAttribute("valoare", String.valueOf(materiePrima.getCantitate() * materiePrima.getPretUnitar()));
                root.appendChild(elemMaterie);
            }

            // Transformatorul
            TransformerFactory tsf = TransformerFactory.newInstance();
            Transformer tf = tsf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File("stoc.xml"));

            // salvarea in xml
            tf.transform(source, streamResult);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}