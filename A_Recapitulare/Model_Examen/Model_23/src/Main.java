import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Produs> citireProduseXML() {
        List<Produs> lista = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("produse.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("produs");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                int cod = Integer.parseInt(element.getElementsByTagName("cod").item(0).getTextContent());
                String denumire = element.getElementsByTagName("denumire").item(0).getTextContent();
                double pret = Double.parseDouble(element.getElementsByTagName("pret").item(0).getTextContent());

                Produs produs = new Produs(cod, denumire,pret);
                lista.add(produs);
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

    public static List<Tranzactie> citireTranzactiiSQL() {
        List<Tranzactie> lista = new ArrayList<>();

        String url = "jdbc:sqlite:tranzactii.db";
        String query = "SELECT * FROM Tranzactii";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int codProdus = res.getInt(1);
                int cantitate = res.getInt(2);
                TIP tip = TIP.valueOf(res.getString(3));

                Tranzactie tranzactie = new Tranzactie(codProdus, cantitate, tip);
                lista.add(tranzactie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Produs> produse = citireProduseXML();
        List<Tranzactie> tranzactii = citireTranzactiiSQL();

        produse.forEach(System.out::println);
        System.out.println();
        tranzactii.forEach(System.out::println);
        System.out.println();

        // Să se afișeze la consolă numărul total de produse
        System.out.println("CERINTA 1: Numarul total de proiecte este: " + produse.stream()
                .count());
        System.out.println();

        // Să se ordoneze produsele alfabetic după denumire și să se afișeze lista acestora la consolă.
        System.out.println("CERINTA 2: Proiectele sortate alfabetic:");
        produse.stream().sorted((p1, p2) -> p1.getDenumire().compareTo(p2.getDenumire()))
                .forEach(produs -> System.out.printf("%-5d %-15s %-5.2f\n",
                        produs.getCod(), produs.getDenumire(), produs.getPret()));
        System.out.println();

//        Denumire Produs, Numar tranzactii
//        Produs A,2
//        Produs B,2
//        Produs C,2
//        Produs D,1
//        Produs E,2

        Map<String, Long> produs_numarTranzactii = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        tranzactie -> produse.stream().filter(produs -> produs.getCod() == tranzactie.getCodProdus())
                                .findFirst().get().getDenumire(),
                        Collectors.counting()
                )).entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1,e2) -> e2,
                                LinkedHashMap::new
                        ));

        System.out.println("CERINTA 3: Numarul de produse din tranzactii");
        produs_numarTranzactii.entrySet().forEach(System.out::println);
        System.out.println();

        // scriere txt
        try(FileOutputStream fis = new FileOutputStream("Date/subiect1/lista.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            for(Map.Entry<String, Long> entry : produs_numarTranzactii.entrySet()) {
                String line = entry.getKey() + " - " + entry.getValue() + System.lineSeparator();
                bw.write(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Să se calculeze valoarea stocurilor pentru fiecare produs, considerând doar tranzacțiile de tip 'intrare'.
        Map<Integer, Integer> codProdus_intrari = tranzactii.stream()
                .filter(tranzactie -> tranzactie.getTip().equals(TIP.intrare))
                .collect(Collectors.groupingBy(
                        Tranzactie::getCodProdus,
                        Collectors.summingInt(Tranzactie::getCantitate)
                ));

        Map<Integer, Integer> codProdus_iesiri = tranzactii.stream()
                .filter(tranzactie -> tranzactie.getTip().equals(TIP.iesire))
                .collect(Collectors.groupingBy(
                        Tranzactie::getCodProdus,
                        Collectors.summingInt(Tranzactie::getCantitate)
                ));

//        Double valoareStocului = produse.stream()
//                .filter(produs -> codProdus_cantitate.get(produs.getCod()) != null)
//                        .mapToDouble(produs -> produs.getPret() * codProdus_cantitate.getOrDefault(produs.getCod(), 0))
//                                .sum();

        Map<Integer, Double> codProdus_valoare = new HashMap<>();
        for (Produs produs : produse) {
            int input = codProdus_intrari.getOrDefault(produs.getCod(), 0);
            int output = codProdus_iesiri.getOrDefault(produs.getCod(), 0);

            int stoc = Math.max(input - output, 0);

            codProdus_valoare.put(produs.getCod(), stoc * produs.getPret());
        }

        Double valoareStocului = codProdus_valoare.entrySet().stream()
                        .mapToDouble(entry -> entry.getValue())
                                .sum();

        System.out.println("CERINTA 4: Valoarea stocurilor este: " + valoareStocului);
        System.out.println();

        // Să se afișeze produsele cu prețul mai mare de 20
        System.out.println("CERINTA 5: Produsele mai scumpe de 20:");
        produse.stream().filter(produs -> produs.getPret() > 20)
                .forEach(produs -> System.out.println(produs.getCod() + ", " + produs.getDenumire() + ", " + produs.getPret()));
        System.out.println();

        // Calcularea și afișarea valorii totale de produse de tip 'intrare':
        Double valoareIntrariri = tranzactii.stream()
                .filter(tranzactie -> tranzactie.getTip().equals(TIP.intrare))
                .collect(Collectors.groupingBy(
                        Tranzactie::getCodProdus,
                        Collectors.summingInt(Tranzactie::getCantitate)
                )).entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * produse.stream()
                        .filter(produs -> produs.getCod() == entry.getKey())
                        .findFirst().get().getPret()).sum();
        System.out.println("Valoarea totală de produse intrate: " + valoareIntrariri);
        System.out.println();

        // Calcularea și afișarea cantității totale de produse de tip 'intrare':
        Integer cantitateIntrari = tranzactii.stream()
                .filter(tranzactie -> tranzactie.getTip().equals(TIP.intrare))
                .collect(Collectors.groupingBy(
                        Tranzactie::getCodProdus,
                        Collectors.summingInt(Tranzactie::getCantitate)
                )).entrySet().stream()
                .mapToInt(entry -> entry.getValue()).sum();
        System.out.println("CERINTA 5: Cantitatea totală de produse intrate: " + cantitateIntrari);
        System.out.println();

        // Să se grupeze tranzacțiile după tip (intrare/iesire) și să se afișeze lista tranzacțiilor pentru fiecare tip.
        Map<TIP, List<Tranzactie>> tip_tranzactii = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        Tranzactie::getTip
                ));
        System.out.println("CERINTA 6: Lista tranzactiilor:");
        for (Map.Entry<TIP, List<Tranzactie>> entry : tip_tranzactii.entrySet()) {
            System.out.println("Tranzactii de tip " + entry.getKey() + ":");
            for (Tranzactie tranzactie : entry.getValue()) {
                System.out.println(tranzactie);
            }
            System.out.println();
        }
        System.out.println();

//        Numărul de produse distincte pentru tranzacțiile de tip 'intrare': 4
//        Numărul de produse distincte pentru tranzacțiile de tip 'iesire': 2
        Map<TIP, Set<Integer>> numarulDeProdusePerTip = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        Tranzactie::getTip,
                        Collectors.mapping(Tranzactie::getCodProdus, Collectors.toSet())
                ));

        System.out.println("CERINTA 7 - Numarul de tranzactii de fiecare tip:");
        for (Map.Entry<TIP, Set<Integer>> entry : numarulDeProdusePerTip.entrySet()) {
            System.out.println("Numărul de produse distincte pentru tranzacțiile de tip " + entry.getKey() + ": " +
                    entry.getValue().stream().count());
        }
        System.out.println();

        // Să se afișeze produsele care nu au nicio tranzacție de tip 'iesire'
        System.out.println("CERINTA 8 - Produsele care nu au nicio tranzacție de tip 'iesire'");
        Set<Integer> tranzactiiIesire = tranzactii.stream().filter(tranzactie -> tranzactie.getTip().equals(TIP.iesire))
                        .map(tranzactie -> tranzactie.getCodProdus())
                                .collect(Collectors.toSet());
        produse.stream()
                .filter(produs -> !tranzactiiIesire.contains(produs.getCod()))
                .forEach(produs -> System.out.println(produs.getDenumire()));
    }
}