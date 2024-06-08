import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Proiect> citireProiecteJSON() {
        List<Proiect> proiecte = new ArrayList<>();

        try(FileInputStream fis  = new FileInputStream("proiecte.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener tokener = new JSONTokener(br);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                int cod = obj.getInt("codProiect");
                String manager = obj.getString("manager");
                String localitate = obj.getString("localitate");
                String strada = obj.getString("strada");
                String obiectiv = obj.getString("obiectiv");
                Double val = obj.getDouble("valoare");

                Proiect proiect = new Proiect(cod, manager, localitate, strada, obiectiv, val);
                proiecte.add(proiect);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return proiecte;
    }

    public static List<Capitol> citireCapitoleSQL() {
        List<Capitol> lista = new ArrayList<>();

        String url = "jdbc:sqlite:devize.db";
        String query = "SELECT * FROM Capitole";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int codCapitol = res.getInt(1);
                int codSantier = res.getInt(2);
                String denumireCheltuiala = res.getString(3);
                String unitateMasura = res.getString(4);
                Double cantitate = res.getDouble(5);
                Double pretUnitar = res.getDouble(6);

                Capitol capitol = new Capitol(codCapitol, codSantier, denumireCheltuiala, unitateMasura, cantitate,pretUnitar);
                lista.add(capitol);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void scriereTXR(List<Capitol> capitole, List<Proiect> proiecte) {
        Map<Integer, List<Capitol>> codCapitol_capitole  = capitole.stream()
                .collect(Collectors.groupingBy(Capitol::getCodSantier));

        try (FileOutputStream fos = new FileOutputStream("devize.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            for (Map.Entry<Integer, List<Capitol>> entry : codCapitol_capitole.entrySet()) {
                bw.write(entry.getKey() + System.lineSeparator());
                List<Capitol> capitoleSortateValoare = entry.getValue().stream()
                        .sorted((c1, c2) -> Double.compare(c2.getCantitate() * c2.getPretUnitar(),
                                c1.getCantitate() * c1.getPretUnitar())).toList();
                for(Capitol capitol : capitoleSortateValoare) {
                    Proiect proiect = proiecte.stream().filter(pr -> pr.getCodProiect() == capitol.getCodSantier())
                            .findFirst().get();
                    String line = "Cod: " + proiect.getCodProiect() + "\t Obiectiv: " + proiect.getObiectiv() +
                            "\t Cheltuiala: " + capitol.getDenumireCheltuiala() +
                            "\t\t Valoarea:" + capitol.getCantitate() * capitol.getPretUnitar() +
                            System.lineSeparator();
                    bw.write(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Proiect> proiecte = citireProiecteJSON();
        List<Capitol> capitole = citireCapitoleSQL();

        // capitole.forEach(System.out::println);

        // CERINTA 1 - proiectele si valoarea medie
        System.out.println("CERINTA 1 - proiectele si valoarea medie");
        proiecte.forEach(System.out::println);

        Double medie = proiecte.stream().mapToDouble(Proiect::getValoare).average().getAsDouble();
        System.out.println("Media proiectelor este de: " + medie);
        System.out.println();

        // CERINTA 2 - cheltuielile fiecarui capitol
        Map<Integer, Double> capitole_cheltuieli = capitole.stream()
                .collect(Collectors.groupingBy(
                        Capitol::getCodCapitol,
                        Collectors.summingDouble(capitol -> capitol.getCantitate() * capitol.getPretUnitar())
                ));

        System.out.println("CERINTA 2 - cheltuielile fiecarui capitol");
        for (Map.Entry<Integer, Double> entry : capitole_cheltuieli.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // CERINTA 3 - situație a cheltuielilor la nivel de capitole, cu sumarizare pe santiere
        Map<Integer, List<Capitol>> codCapitol_capitole = capitole.stream()
                .collect(Collectors.groupingBy(Capitol::getCodSantier));

        System.out.println("CERINTA 3 - situație a cheltuielilor la nivel de capitole, cu sumarizare pe santiere");
        for (Map.Entry<Integer, List<Capitol>> entry : codCapitol_capitole.entrySet()) {
            System.out.println(entry.getKey());
            for(Capitol cap : entry.getValue()) {
                Proiect proiect = proiecte.stream().filter(pr -> pr.getCodProiect() == cap.getCodSantier()).findFirst().get();
                System.out.println(proiect.getObiectiv() + " - " + cap.getCantitate() * cap.getPretUnitar());
            }
        }

        // CERINTA 3 - In fisier TXT ordonat descrescator
        scriereTXR(capitole, proiecte);
        System.out.println();

        // CERINTA 4 - Să se afișeze la consolă capitolele care au cheltuieli totale mai mari de o anumită valoare.
        Map<Integer, Double> capitole_valoare = capitole.stream().collect(
                Collectors.groupingBy(
                        Capitol::getCodCapitol,
                        Collectors.summingDouble(capitol -> capitol.getPretUnitar() * capitol.getCantitate())
                )).entrySet().stream()
                .filter(entry -> entry.getValue() > 30000)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));

        System.out.println("CERINTA 4 - Să se afișeze la consolă capitolele care au cheltuieli totale mai mari de o anumită valoare");
        for (Map.Entry<Integer, Double> entry : capitole_valoare.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        // CERINTA 5: Să se afișeze la consolă suma cheltuielilor pentru fiecare localitate.
        Map<Integer, Double> codCapitol_valoareCheltuiala = capitole.stream()
                .collect(Collectors.groupingBy(
                        Capitol::getCodCapitol,
                        Collectors.summingDouble(capitol -> capitol.getCantitate() * capitol.getPretUnitar())
                ));

        Map<String, Double> localitate_cheltuieli = proiecte.stream()
                .collect(Collectors.toMap(
                        Proiect::getLocalitate,
                        proiect -> codCapitol_valoareCheltuiala.getOrDefault(proiect.getCodProiect(), 0.0),
                        Double::sum
                ));
        System.out.println();

        System.out.println("CERINTA 5 - suma cheltuielilor pentru fiecare localitate");
        for (Map.Entry<String, Double> entry : localitate_cheltuieli.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // CERINTA 6: Să se afișeze la consolă capitolele care au cheltuieli totale între două valori.
        Map<Integer, Double> capitole_intre_doua_valori = capitole.stream()
                .collect(Collectors.groupingBy(
                        Capitol::getCodCapitol,
                        Collectors.summingDouble(capitol -> capitol.getPretUnitar() * capitol.getCantitate())
                )).entrySet().stream()
                .filter(entry -> entry.getValue() < 100000 && entry.getValue() > 45000)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));

        System.out.println("CERINTA 6 - capitolele care au cheltuieli totale între două valori");
        for (Map.Entry<Integer, Double> entry : capitole_intre_doua_valori.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // CERINTA 7: Să se afișeze la consolă capitolele cu cel mai mare și cel mai mic cost total.
        Map<Integer, Double> maxEntry = codCapitol_valoareCheltuiala.entrySet().stream()
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                .stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (es1, es2) -> es2,
                        LinkedHashMap::new
                ));

        System.out.println("CERINTA 7 - Capitolul cu cheltuiala maxima:");
        for (Map.Entry<Integer, Double> entry : maxEntry.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // CERINTA 8: Să se afișeze la consolă capitolele și numărul de santiere aferente fiecărui capitol.
        Map<Integer, Long> capitol_nrSantiere = capitole.stream()
                .collect(Collectors.groupingBy(
                                Capitol::getCodCapitol,
                                Collectors.mapping(capitol -> capitol.getCodSantier(), Collectors.toSet())
                        )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (long) entry.getValue().size()
                ));

        System.out.println("CERINTA 8 - Capitolele și numărul de santiere aferente fiecărui capitol:");
        for (Map.Entry<Integer, Long> entry : capitol_nrSantiere.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}