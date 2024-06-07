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
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Angajat> citireAngajatiTXT() {
        List<Angajat> lista = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("angajati.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);)
        {
            String line = br.readLine();
            while(line != null) {
                String values[] = line.split(",");
                int id = Integer.parseInt(values[0].trim());
                String nume = values[1].trim();
                String departament = values[2].trim();
                Double salariul = Double.parseDouble(values[3].trim());

                Angajat ang = new Angajat(id, nume, departament, salariul);
                lista.add(ang);

                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

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
                String nume = obj.getString("nume_proiect");
                Double buget = obj.getDouble("buget");

                List<Integer> echipa  = new ArrayList<>();
                JSONArray echipaArr = obj.getJSONArray("echipa");
                for (int j = 0; j < echipaArr.length(); j++) {
                    int codAng = echipaArr.getInt(j);
                    echipa.add(codAng);
                }

                Proiect proiect = new Proiect(cod, nume, buget, echipa);
                lista.add(proiect);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Evaluare> citireEvaluariXML() {
        List<Evaluare> lista = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("evaluari.xml"));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList nodeList = doc.getElementsByTagName("evaluare");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);

                int id = Integer.parseInt(elem.getElementsByTagName("id_angajat").item(0).getTextContent());
                int an = Integer.parseInt(elem.getElementsByTagName("an").item(0).getTextContent());
                Double scor = Double.parseDouble(elem.getElementsByTagName("scor").item(0).getTextContent());

                Evaluare eval = new Evaluare(id, an,scor);
                lista.add(eval);
            }
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<ProiectFinalizat> citireProiecteFinalizareSQL() {
        List<ProiectFinalizat> lista = new ArrayList<>();

        String url = "jdbc:sqlite:proiecte_finalizate.db";
        String query = "SELECT * FROM proiecte_finalizate";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int cod = res.getInt("cod_proiect");
                String nume = res.getString("nume_proiect");
                int anFinalizare = res.getInt("an_finalizare");

                ProiectFinalizat proiectFinalizat = new ProiectFinalizat(cod,nume,anFinalizare);
                lista.add(proiectFinalizat);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void scriereAngajatiBinar(List<Angajat> lista) {
        try (FileOutputStream fos = new FileOutputStream("angajati.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);)
        {
            for (Angajat ang : lista) {
                oos.writeObject(ang);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sciereProiecteFinalizateJSON(List<ProiectFinalizat> lista) {
            JSONArray array = new JSONArray();

            for(ProiectFinalizat proiectFinalizat : lista) {
                JSONObject obj = new JSONObject();
                obj.put("cod_proiect", String.valueOf(proiectFinalizat.getCod_proiect()));
                obj.put("nume_proiect", proiectFinalizat.getNume_proiect());
                obj.put("an_finalizare", String.valueOf(proiectFinalizat.getAn_finalizare()));

                array.put(obj);
            }

            try (FileWriter fw = new FileWriter("proiecte_finalizate.json")) {
                fw.write(array.toString(4));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public static void scriereEvaluariXML(List<Evaluare> lista) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("evaluari");
            doc.appendChild(root);

            for (Evaluare evaluare : lista) {
                Element elem = doc.createElement("evaluare");
                elem.setAttribute("id_angajat", String.valueOf(evaluare.getId_angajat()));
                elem.setAttribute("an", String.valueOf(evaluare.getAn()));
                elem.setAttribute("scor", String.valueOf(evaluare.getScor()));

                root.appendChild(elem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File("evaluari_sortate.xml"));

            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Angajat> angajati = citireAngajatiTXT();
        List<Proiect> proiecte = citireProiecteJSON();
        List<Evaluare> evaluari = citireEvaluariXML();
        List<ProiectFinalizat> proiecteFinalizate = citireProiecteFinalizareSQL();

        // Cerinta 1 - Salariul total pe departamente
        Map<String, Double> salariulDepartamente = angajati.stream().collect(
                Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.summingDouble(Angajat::getSalariu)
                )).entrySet().stream().sorted((a1, a2) -> a2.getValue().compareTo(a1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));

        System.out.println("CERINTA 1: Salariul pe departamente:");
        for (Map.Entry<String, Double> entry : salariulDepartamente.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // Cerinta 2 - angajatii cu salarii peste media departamentelor
        Map<String, Double> salariulMediuDepartamente = angajati.stream()
                .collect(
                        Collectors.groupingBy(
                                Angajat::getDepartament,
                                Collectors.averagingDouble(Angajat::getSalariu)
                        ));

        List<Angajat> angajatiCuSalariulPesteMedie = angajati.stream()
                .filter(angajat -> angajat.getSalariu() > salariulMediuDepartamente.getOrDefault(angajat.getDepartament(), 0.0))
                .toList();

        System.out.println("CERINTA 2: Angajatii cu salarii peste medie:");
        System.out.println("Departament, ID, Nume, Salariul, Salariul Mediu");
        for (Angajat angajat : angajatiCuSalariulPesteMedie) {
            Double salariulMediuDep = salariulMediuDepartamente.getOrDefault(angajat.getDepartament(), 0.0);
            System.out.println(angajat.getDepartament() + " " + angajat.getId() + " " +
                    angajat.getNume() + " " + angajat.getSalariu() + " " + salariulMediuDep);
        }
        System.out.println();

        List<Angajat> angajatiDupaSalariu = angajati.stream()
                .sorted((a1, a2) -> Double.compare(a2.getSalariu(), a1.getSalariu())).toList();

        System.out.println("CERINTA 3: Topul 3 angajati dupa salariu:");
        for (int i = 0; i < 3; i++) {
            Angajat ang = angajatiDupaSalariu.get(i);
            System.out.println(ang.getNume() + " " + ang.getId() + " " + ang.getSalariu());
        }

        // Salvarea angajatilor sortati descrescator in fucntie de salariu in fisier binar
        scriereAngajatiBinar(angajatiDupaSalariu);

        // Salvare in JSON a proiectelor finalizate in 2022 sau 2023
        List<ProiectFinalizat> proiectFinalizate2022_2023 = proiecteFinalizate.stream()
                .filter(pr -> pr.getAn_finalizare() == 2022 || pr.getAn_finalizare() == 2023).toList();
        sciereProiecteFinalizateJSON(proiectFinalizate2022_2023);

        // Salvarea evaluarilor sortate dupa scor descrescator intr-un fisier XML
        List<Evaluare> evaluariSortate = evaluari.stream()
                .sorted((e1, e2) -> Double.compare(e2.getScor(), e1.getScor())).toList();
        scriereEvaluariXML(evaluariSortate);
    }
}