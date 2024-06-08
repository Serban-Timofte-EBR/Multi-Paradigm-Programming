import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Proiect> citireProiecteJSON() {
        List<Proiect> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("proiecte.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int codCompanie = object.getInt("cod_proiect");
                String nume = object.getString("nume_proiect");
                String manager = object.getString("manager");
                Double buget = object.getDouble("buget");

                Proiect proiect = new Proiect(codCompanie, nume, manager, buget);
                lista.add(proiect);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Cheltuiala> citireCheltuieliSQL() {
        List<Cheltuiala> lista = new ArrayList<>();

        String url = "jdbc:sqlite:cheltuieli.db";
        String query = "SELECT * FROM Cheltuieli";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int cod_cheltuiala = res.getInt("cod_cheltuiala");
                int cod_proiect = res.getInt("cod_proiect");
                String descriere = res.getString("descriere");
                String unitate_masura = res.getString("unitate_masura");
                Double cantitate = res.getDouble("cantitate");
                Double pret_unitar = res.getDouble("pret_unitar");

                Cheltuiala cheltuiala = new Cheltuiala(cod_cheltuiala, cod_proiect, descriere, unitate_masura,
                        cantitate, pret_unitar);
                lista.add(cheltuiala);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Proiect> proiecte = citireProiecteJSON();
        List<Cheltuiala> cheltuieli = citireCheltuieliSQL();

        // Să se afișeze la consolă proiectele și valoarea medie a bugetelor acestora
        System.out.println("CERINTA 1 - Lista proiectelor:");
        proiecte.forEach(System.out::println);
        Double valoareaMedie = proiecte.stream()
                .mapToDouble(Proiect::getBuget)
                .average().getAsDouble();
        System.out.println("Valaorea medie a proiectelor este: " + valoareaMedie);
        System.out.println();

        // Să se afișeze la consolă cantitățile totale pentru fiecare cheltuială
        Map<Integer, Double> cheltuialaValoare = cheltuieli.stream()
                .collect(Collectors.toMap(
                        Cheltuiala::getCod_cheltuiala,
                        cheltuiala -> cheltuiala.getCantitate() * cheltuiala.getPret_unitar()
                ));
        System.out.println("CERINTA 2 - Valorile cheltuielilor");
        cheltuialaValoare.entrySet().forEach(System.out::println);
        System.out.println();

        // Să se afișeze lista managerilor și numărul de proiecte gestionate de fiecare.
        Map<String, Long> manager_numerProiecte = proiecte.stream()
                .collect(Collectors.groupingBy(
                        Proiect::getManager,
                        Collectors.counting()
                ));
        System.out.println("CERINTA 3 - Managerii si numarul de proiecte");
        for (Map.Entry<String, Long> entry : manager_numerProiecte.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        // Să se afișeze bugetul total pentru fiecare manager, ordonat descrescător după buget
        Map<String, Double> manager_bugetTotal = proiecte.stream()
                .collect(Collectors.groupingBy(
                        Proiect::getManager,
                        Collectors.summingDouble(Proiect::getBuget)
                )).entrySet().stream()
                .sorted((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e2,
                        LinkedHashMap::new
                ));

        System.out.println("CERONTA 4 - Managerii si bugetul total");
        manager_bugetTotal.entrySet().forEach(System.out::println);
        System.out.println();

        // Să se salveze în fișierul raport_cheltuieli.txt o situație a cheltuielilor la nivel de proiecte, cu sumarizare pe cheltuieli
        Map<Integer, List<Cheltuiala>> codProiect_listaCheltuieli = cheltuieli.stream()
                .collect(Collectors.groupingBy(
                        Cheltuiala::getCod_proiect
                ));

        System.out.println("CERINTA 5 - Cheltuielile per proiect:");
        for (Map.Entry<Integer, List<Cheltuiala>> entry : codProiect_listaCheltuieli.entrySet()) {
            System.out.println(entry.getKey());
            for(Cheltuiala cheltuiala : entry.getValue()) {
                System.out.println(cheltuiala.getDescriere() + " - " + cheltuiala.getPret_unitar() * cheltuiala.getCantitate());
            }
        }
        System.out.println();
    }
}