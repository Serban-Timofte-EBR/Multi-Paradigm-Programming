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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Departament> citireDepartamenteJSON() {
        List<Departament> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("departamente.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int codDepartament = obj.getInt("codDepartament");
                String denumire = obj.getString("denumire");
                String manager = obj.getString("manager");
                Double buget = obj.getDouble("buget");

                Departament departament = new Departament(codDepartament, denumire, manager, buget);
                lista.add(departament);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Angajat> citireAngajatiXML() {
        List<Angajat> lista = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("angajati.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("angajat");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);

                int cod = Integer.parseInt(elem.getElementsByTagName("codAngajat").item(0).getTextContent());
                String nume = elem.getElementsByTagName("nume").item(0).getTextContent();
                int departament = Integer.parseInt(elem.getElementsByTagName("departament").item(0).getTextContent());
                Double salariul = Double.parseDouble(elem.getElementsByTagName("salariu").item(0).getTextContent());

                Angajat angajat = new Angajat(cod, nume, departament, salariul);
                lista.add(angajat);
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
        List<Departament> departamente = citireDepartamenteJSON();
        List<Angajat> angajati = citireAngajatiXML();

        // angajati.forEach(System.out::println);
        // departamente.forEach(System.out::println);

        // Să se afișeze la consolă departamentele și bugetul mediu al acestora.
        System.out.println("CERINTA 1 - Departamentele si bugetul acestora:");
        for (Departament departament : departamente) {
            System.out.println(departament.getDenumire() + " - " + departament.getBuget());
        }
        System.out.println();

        // Să se afișeze la consolă salariile totale pentru fiecare departament
        Map<Integer, Double> departament_salariulTotal = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.summingDouble(Angajat::getSalariul)
                )).entrySet().stream().sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e2,
                                LinkedHashMap::new
                        ));

        System.out.println("CERINTA 2: Salariile totale in departamente");
        for(Map.Entry<Integer, Double> entry : departament_salariulTotal.entrySet()) {
            Departament departament = departamente.stream()
                            .filter(dep -> dep.getCodDepartament() == entry.getKey()).findFirst().get();
            System.out.println(departament.getDenumire() + " - " + entry.getValue());
        }

        // Să se salveze în fișierul departamente_cheltuieli.xml o situație a salarilor la nivel de departamente

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("departamente");
            doc.appendChild(root);

            for(Map.Entry<Integer, Double> entry : departament_salariulTotal.entrySet()) {
                Departament departament = departamente.stream()
                        .filter(dep -> dep.getCodDepartament() == entry.getKey()).findFirst().get();

                Element element = doc.createElement("departament");
                element.setAttribute("Denumire_Departament", departament.getDenumire());
                element.setAttribute("Valoarea_salariilor", String.valueOf(entry.getValue()));

                root.appendChild(element);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("departamente_cheltuieli.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        // Să se afișeze lista angajaților din departamentul cu cel mai mare buget, ordonată alfabetic.
        System.out.println("CERINTA 3: Angajatii sortati alfabetic din departamentul cu cel mai mare buget");
        Departament departamentCuBugetMaxim = departamente.stream()
                .sorted((d1, d2) -> Double.compare(d2.getBuget(), d2.getBuget()))
                .findFirst().get();

        angajati.stream().filter(angajat -> angajat.getDepartament() == departamentCuBugetMaxim.getCodDepartament())
                .sorted((a1, a2) -> a1.getNume().compareTo(a2.getNume()))
                .forEach(angajat -> System.out.println(angajat.getNume() + " - Departament: " +
                        angajat.getDepartament()));
        System.out.println();

        // Să se afișeze departamentele care au mai mult de 3 angajați.
        System.out.println("CERINTA 4: Departamentele cu mai mult de 1 angajati");
        angajati.stream().collect(Collectors.groupingBy(
                Angajat::getDepartament,
                Collectors.counting()
        )).entrySet().stream().filter(entry -> entry.getValue() > 1)
                .forEach(entry -> System.out.println("Departament: " + entry.getKey() + " - " + "Nr. Angajati: " + entry.getValue()));
        System.out.println();

        // Să se afișeze salariul maxim și minim din fiecare departament.
        System.out.println("CERINTA 5: Salariul minim si maxim din fiecare departament");
        Map<Integer, List<Double>> departament_salariulMaximMinim = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.mapping(angajat -> angajat.getSalariul(), Collectors.toList())
                ));

        for(Map.Entry<Integer, List<Double>> entry : departament_salariulMaximMinim.entrySet()) {
            Double max = entry.getValue().stream().max(Double::compare).get();
            Double min = entry.getValue().stream().min(Double::compare).get();

            System.out.println("Departament: " + entry.getKey() + " - " + "Sal. Min: " + min + "\tSal. Max: " + max);
        }
        System.out.println();

        // Să se afișeze angajații care au salariul mai mare decât bugetul mediu al departamentului lor.
        System.out.println("CERINTA 6 - Angajati cu salarii peste media departamentului:");

        Map<Integer, Double> salariiMediiPerDepartament = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.averagingDouble(Angajat::getSalariul)
                ));

        List<Angajat> angajatiCuSalariiPesteMediaDepartamentului = angajati.stream()
                .filter(angajat -> angajat.getSalariul() > salariiMediiPerDepartament.getOrDefault(angajat.getDepartament(), 0.0))
                .toList();

        angajatiCuSalariiPesteMediaDepartamentului.forEach(angajat ->
                System.out.println(angajat.getNume()));
        System.out.println();

        // Să se afișeze departamentele în care există cel puțin un angajat cu salariul mai mare de 7000.
        System.out.println("CERINTA 7 - Departamentele in care exista cel putin un salariu mai mare de 7000");

        Map<Integer, Optional<Angajat>> salariiMaximeDepartament = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.maxBy((a1, a2) -> a1.getSalariul().compareTo(a2.getSalariul()))
                ));

        for (Map.Entry<Integer, Optional<Angajat>> entry : salariiMaximeDepartament.entrySet()) {
            if(entry.getValue().get().getSalariul() > 7000) {
                System.out.println(entry.getKey());
            }
//            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // Să se afișeze lista angajaților care au numele de familie identic cu cel al managerului departamentului.
        List<String> manageri = departamente.stream()
                        .map(departament -> departament.getManager()).toList();

        List<String> angajatiManageri = angajati.stream()
                .filter(angajat -> manageri.indexOf(angajat.getNume()) != -1)
                .map(angajat -> angajat.getNume())
                .toList();

        System.out.println("Angajatii care au acelasi nume ca managerul");
        for (String angajat : angajatiManageri) {
            System.out.println(angajat);
        }

        // Să se implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:
            // Componenta client trimite serverului un cod de departament.
            // Componenta server va întoarce clientului denumirea și bugetul departamentului respectiv.
            // Componenta server poate servi oricâte cereri.

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();

        int port = 12345;

        try (ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul a pornit!");
            while (true) {
                Socket client = server.accept();
                new Thread(new ServerHandler(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}