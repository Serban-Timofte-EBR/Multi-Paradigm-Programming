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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static List<Companie> citereCompaniiJSON() {
        List<Companie> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("companii.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int cod = object.getInt("codCompanie");
                String denumire = object.getString("denumire");
                String localitate = object.getString("localitate");
                Double cifraAfaceri = object.getDouble("cifraAfaceri");

                Companie companie = new Companie(cod, denumire, localitate, cifraAfaceri);
                lista.add(companie);
            }

        } catch (FileNotFoundException e) {
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
                Element element = (Element) nodeList.item(i);

                int codAngajat = Integer.parseInt(element.getElementsByTagName("codAngajat").item(0).getTextContent());
                int codCompanie = Integer.parseInt(element.getElementsByTagName("codCompanie").item(0).getTextContent());
                String nume = element.getElementsByTagName("nume").item(0).getTextContent();
                Double salariul = Double.parseDouble(element.getElementsByTagName("salariu").item(0).getTextContent());

                Angajat angajat = new Angajat(codAngajat, codCompanie, nume, salariul);
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

    public static void scriereTXT(List<Angajat> angajati, List<Companie> companii) {
        try (FileOutputStream fos = new FileOutputStream("desfasurator_cheltuieli.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            Map<Integer, List<Angajat>> angajatiPerCompanie = angajati.stream()
                    .collect(Collectors.groupingBy(Angajat::getCodCompanie));

            for(Map.Entry<Integer, List<Angajat>> entry : angajatiPerCompanie.entrySet()) {
                Companie companie = companii.stream().filter(companie1 -> companie1.getCodCompanie() == entry.getKey())
                        .findFirst().get();

                String header = entry.getKey() + " - " + companie.getDenumire() + System.lineSeparator();
                bw.write(header);

                for(Angajat angajat : entry.getValue()) {
                    String line = "\t" + angajat.getCodAngajat() + " - " + angajat.getSalariul() + System.lineSeparator();
                    bw.write(line);
                }
                bw.write(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Companie> companii = citereCompaniiJSON();
        List<Angajat> angajati = citireAngajatiXML();

//        companii.forEach(System.out::println);
//        angajati.forEach(System.out::println);

        // Să se afișeze la consolă companiile și cifra de afaceri medie a acestora (media cifrelor de afaceri).
        System.out.println("CERINTA 1: Lista de companii");
        companii.forEach(companie -> System.out.printf(
                "%-2d %-25s %-15s %f\n",
                companie.getCodCompanie(), companie.getDenumire(), companie.getLocalitate(), companie.getCifraDeAfaceri()
        ));
        Double cifraDeAfaceriMedie = companii.stream()
                .mapToDouble(Companie::getCifraDeAfaceri).average().getAsDouble();
        System.out.println("Cifra de afaceri medie este: " + cifraDeAfaceriMedie);
        System.out.println();

        // Să se afișeze la consolă salariul total pentru fiecare companie
        Map<Integer, Double> companie_bugetSalarii = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodCompanie,
                        Collectors.summingDouble(Angajat::getSalariul)
                ));

        System.out.println("CERINTA 2 - Bugetul de salarii pe companie");
        for (Map.Entry<Integer, Double> entry : companie_bugetSalarii.entrySet()) {
            Companie companie = companii.stream()
                    .filter(companie1 -> companie1.getCodCompanie() == entry.getKey())
                    .findFirst().get();
            System.out.printf("%-20s - %-15f\n", companie.getDenumire(), entry.getValue());
        }
        System.out.println();

        // Să se salveze în fișierul cheltuieli.txt o situație a cheltuielilor la nivel de companii, cu sumarizare pe angajați
        Map<Integer, List<Angajat>> angajatiPerCompanie = angajati.stream()
                .collect(Collectors.groupingBy(Angajat::getCodCompanie));

        System.out.println("CERINTA 3 - Lista de salarii per companie");
        for(Map.Entry<Integer, List<Angajat>> entry : angajatiPerCompanie.entrySet()) {
            Companie companie = companii.stream().filter(companie1 -> companie1.getCodCompanie() == entry.getKey())
                            .findFirst().get();
            System.out.println(entry.getKey() + " - " + companie.getDenumire());
            for(Angajat angajat : entry.getValue()) {
                System.out.println(angajat.getCodAngajat() + " - " + angajat.getSalariul());
            }
            System.out.println();
        }
        scriereTXT(angajati, companii);

        // Să se afișeze la consolă lista companiilor ordonate descrescător după cifra de afaceri.
        List<Companie> companiiCifraDeAfaceriDescrescator = companii.stream()
                .sorted((c1, c2) -> Double.compare(c2.getCifraDeAfaceri(), c1.getCifraDeAfaceri()))
                .toList();
        System.out.println("CERINTA 4 - Lista de companii sortate descrescator dupa cifra de afaceri");
        for (Companie companie : companiiCifraDeAfaceriDescrescator) {
            System.out.printf("Compania: %-20s \t-\t Cifra de afaceri: %f\n",
                    companie.getDenumire(), companie.getCifraDeAfaceri());
        }
        System.out.println();

        // Să se afișeze la consolă lista angajaților ordonată alfabetic după nume.
        System.out.println("CERINTA 5 - Lista de angajati sortata alfabetic");
        angajati.stream()
                .sorted((a1, a2) -> a1.getNume().compareTo(a2.getNume()))
                .toList()
                .stream().forEach(angajat -> System.out.println(angajat.getNume()));
        System.out.println();

        // Să se afișeze la consolă compania cu cei mai mulți angajați.
        Map.Entry<Integer, Long> companie_nrAngajati = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodCompanie,
                        Collectors.counting()
                )).entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .get();
        Integer codCompanieCautata = companie_nrAngajati.getKey();
        Companie companieCautata = companii.stream()
                        .filter(companie -> companie.getCodCompanie() == codCompanieCautata)
                                .findFirst().get();
        System.out.println("CERINTA 6:");
        System.out.println("Compania cu cel mai mare numar de angajati este " + companieCautata.getDenumire() + "( cod: " +
                companie_nrAngajati.getKey() + " )");
        System.out.println("\t\tNumar angajati: " + companie_nrAngajati.getValue());
        System.out.println();

        // Să se afișeze la consolă angajatul cu cel mai mare salariu.

        Angajat angajatCuSalariulMaxim = angajati.stream().max((a1, a2) -> Double.compare(a1.getSalariul(), a2.getSalariul()))
                .get();
        System.out.println("CERINTA 7 - Angajatul cu salariul maxim:");
        System.out.println(angajatCuSalariulMaxim.getNume() + " - " + angajatCuSalariulMaxim.getSalariul() + " RON/luna");
        System.out.println();

        // Să se afișeze la consolă media salariilor pentru fiecare companie.
        Map<Integer, Double> companie_salariuMediu = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodCompanie,
                        Collectors.averagingDouble(Angajat::getSalariul)
                ));
        System.out.println("CERINTA 8 - Lista de salarii medii:");
        for(Map.Entry<Integer, Double> entry : companie_salariuMediu.entrySet()){
            Companie companie = companii.stream().filter(companie1 -> companie1.getCodCompanie() == entry.getKey())
                    .findFirst().get();
            System.out.printf("%-20s   -   %f\n", companie.getDenumire(), entry.getValue());
        }
        System.out.println();

        // Să se afișeze la consolă lista companiilor care au cifra de afaceri mai mare de 1.000.000.
        List<Companie> companiiCuCifreDeAfacereMari = companii.stream()
                .filter(companie -> companie.getCifraDeAfaceri() > 1000000)
                .toList();

        System.out.println("CERINTA 9 - Lista de companii cu cifra de afaceri peste 1.000.000");
        for(Companie companie : companiiCuCifreDeAfacereMari) {
            System.out.printf("%-20s -   %6.2f\n", companie.getDenumire(), companie.getCifraDeAfaceri());
        }
        System.out.println();

        // Să se afișeze la consolă angajații care au salarii mai mari decât media salariilor din compania lor.
        Map<Integer, Double> companie_medieSalariala = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodCompanie,
                        Collectors.averagingDouble(Angajat::getSalariul)
                ));

        List<Angajat> angatiCuSalariiPesteMedie = angajati.stream()
                .filter(angajat -> angajat.getSalariul() > companie_medieSalariala.get(angajat.getCodCompanie()))
                .sorted((a1, a2) -> Double.compare(a2.getSalariul(), a1.getSalariul()))
                .toList();
        System.out.println("CERITNA 10 - Lista cu angajati cu salarii peste media companiei:");
        angatiCuSalariiPesteMedie.forEach(angajat ->
                System.out.printf("%-15s -   %5.2f \t\t Cod companie: %d\n", angajat.getNume(), angajat.getSalariul(), angajat.getCodCompanie()));
        System.out.println();

        // Să se afișeze la consolă localitate cu cifra de afaceri totală cea mai mare - sumând cifrele de afaceri ale companiilor din aceeași localitate).
        Map<String, Double> localitati_cifreDeAfaceri = companii.stream()
                .collect(Collectors.groupingBy(
                        Companie::getLocalitate,
                        Collectors.summingDouble(Companie::getCifraDeAfaceri)
                ));
        System.out.println("CERINTA 11 - Localitatea cu cifra de afaceri cea mai mare");
        Map.Entry<String, Double> localitateaCuCeaMaiMareCifraDeAfaceri = localitati_cifreDeAfaceri.entrySet().stream()
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                .get();
        System.out.println(localitateaCuCeaMaiMareCifraDeAfaceri.getKey() + " - " + localitateaCuCeaMaiMareCifraDeAfaceri.getValue());

        // Să se afișeze la consolă lista companiilor dintr-o localitate specificată (localitatea se citește de la tastatură).
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numele localitaii: ");
        String localitatea = scanner.nextLine();
        System.out.println();

        List<Companie> companiiDinLocalitate = companii.stream()
                .filter(companie -> companie.getLocalitate().equals(localitatea))
                .toList();
        System.out.println("CERINTA 12 - Companiile din localitatea cautata");
        System.out.println("Localitatea: " + localitatea);
        companiiDinLocalitate.forEach(companie ->
                System.out.println(companie.getDenumire() + " - " + companie.getLocalitate()));
        System.out.println();

        // Să se afișeze la consolă numele angajaților împreună cu denumirea companiei lor. + scriere XML
        System.out.println("CERINTA 12 - Organigrama companiilor");
        Map<Integer, List<Angajat>> companie_angajati = angajati.stream()
                .collect(Collectors.groupingBy(Angajat::getCodCompanie));

        for(Companie companie : companii) {
            System.out.println(companie.getDenumire());

            List<Angajat> angajatiiCompaniei = companie_angajati.get(companie.getCodCompanie());
            for(Angajat angajat : angajatiiCompaniei) {
                System.out.println(angajat.getNume() + " - " + angajat.getSalariul());
            }
            System.out.println();
        }

        // Scriem intr-un fisier JSON organigrama

        try (FileOutputStream fos = new FileOutputStream("organigrama.json");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            JSONArray jsonArray = new JSONArray();

            for(Companie companie : companii) {
                JSONObject object = new JSONObject();

                object.put("DenumireCompanie", companie.getDenumire());

                JSONArray angajatiArray = new JSONArray();
                List<Angajat> angajatiiCompaniei = companie_angajati.get(companie.getCodCompanie());
                for(Angajat angajat : angajatiiCompaniei) {
                    JSONObject objAngajat = new JSONObject();
                    objAngajat.put("Nume", angajat.getNume());
                    objAngajat.put("Salariul", angajat.getSalariul());

                    angajatiArray.put(objAngajat);
                }

                object.put("Angajati", angajatiArray);
                jsonArray.put(object);
            }

            try (FileWriter fw = new FileWriter("organigrama.json")) {
                fw.write(jsonArray.toString(4));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Scriem in XML compania, numarul de angajati si cifra de afaceri
        Map<Integer, Long> compania_nrAngajati = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodCompanie,
                        Collectors.counting()
                ));

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("companii");
            doc.appendChild(root);

            for(Companie companie : companii) {
                Long numarulDeAngjati = compania_nrAngajati.entrySet().stream()
                        .filter(integerLongEntry -> integerLongEntry.getKey() == companie.getCodCompanie())
                        .findFirst().get().getValue();

                Element element = doc.createElement("companie");
                element.setAttribute("Denumire", companie.getDenumire());
                element.setAttribute("NumarAngajati", String.valueOf(numarulDeAngjati));
                element.setAttribute("CifraDeAfaceri", String.valueOf(companie.getCifraDeAfaceri()));

                root.appendChild(element);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("detalii_companii.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

//        Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:
//              Componenta client TCP/IP trimite serverului un cod de companie, iar componenta server va întoarce
//                  clientului TCP denumirea companiei și cifra de afaceri a companiei.
//              Componenta server poate servi oricâte cereri.

        int port = 12345;

        try (ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul a fost pornit!");
            while (true) {
                Socket client = server.accept();
                new Thread(new ServerHandlerRequests(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}