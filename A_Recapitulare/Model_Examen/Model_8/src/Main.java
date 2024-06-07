import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Carte> citireCartiTXT() {
        List<Carte> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("carti.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split("\t");
                String cota = values[0].trim();
                String titlu = values[1].trim();
                String autor = values[2].trim();
                int an = Integer.parseInt(values[3].trim());

                Carte carte = new Carte(cota, titlu,autor,an);
                lista.add(carte);

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

    public static List<Cititor> citireCititoriSQL() {
        List<Cititor> lista = new ArrayList<>();

        String url = "jdbc:sqlite:biblioteca.db";
        String query = "SELECT * FROM Imprumuturi";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                String nume = res.getString(1);
                String cota = res.getString(2);
                int nrZile = res.getInt(3);

                Cititor cititor = new Cititor(nume, cota, nrZile);
                lista.add(cititor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void scriereCititoriOrdonatiTXT(Map<String, Integer> cititori) {
        try(FileOutputStream fos = new FileOutputStream("cititori_ordonati.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            bw.write("Nume Student                   - Total zile imprumut" + System.lineSeparator());
            for(Map.Entry<String, Integer> entry : cititori.entrySet()) {
                bw.write(entry.getKey() + "                   - " + entry.getValue() + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Carte> carti = citireCartiTXT();
        List<Cititor> cititori = citireCititoriSQL();

        // carti.forEach(System.out::println);
        // cititori.forEach(System.out::println);

        List<Carte> cartiInainteDe1940 = carti.stream()
                .filter(carte -> carte.anPublicare < 1940).toList();
        System.out.println("CERINTA 1:");
        for (Carte carte: cartiInainteDe1940) {
            System.out.printf("%-10s %-30s %-20s %d\n", carte.cotaCarte, carte.titlu, carte.autor, carte.anPublicare);
        }
        System.out.println();

        System.out.println("CERINTA 2:");
        Map<String, List<Cititor>> cititoriUnici = cititori.stream().collect(
                Collectors.groupingBy(Cititor::getNume));

        for(Map.Entry<String, List<Cititor>> entry : cititoriUnici.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println();

        System.out.println("CERINTA 3:");
        Map<String, Integer> cititoriNumarZileImprumutate = cititori.stream().collect(
                Collectors.groupingBy(
                        Cititor::getNume,
                        Collectors.summingInt(Cititor::getNumarZile)
                )).entrySet().stream()
                .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e2,
                                LinkedHashMap::new
                        ));
        //cititoriNumarZileImprumutate.entrySet().forEach(System.out::println);
        scriereCititoriOrdonatiTXT(cititoriNumarZileImprumutate);
        System.out.println();
    }
}