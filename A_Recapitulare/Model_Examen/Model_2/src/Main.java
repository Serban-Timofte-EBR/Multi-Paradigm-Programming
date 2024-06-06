import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Persoana> citirePersoaneSQL() {
        List<Persoana> lista = new ArrayList<>();

        String url = "jdbc:sqlite:bursa.db";
        String query = "SELECT * FROM Persoane";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query)) {
                while (res.next()) {
                    int cod = res.getInt("Cod");
                    String cnp = res.getString("CNP");
                    String nume = res.getString("Nume");

                    Persoana pers = new Persoana(cod, cnp, nume);

                    lista.add(pers);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Tranzactii> citireTXT() {
        List<Tranzactii> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("bursa_tranzactii.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)){
                String line = br.readLine();
                while(line != null) {
                    String values[] = line.split(",");
                    int cod = Integer.parseInt(values[0].trim());
                    String simbol = values[1].trim();
                    String tip = values[2].trim();
                    int cantitate = Integer.parseInt(values[3].trim());
                    double pret = Double.parseDouble(values[4].trim());

                    Tranzactii tranzactie = new Tranzactii(cod, simbol, tip, cantitate, pret);
                    lista.add(tranzactie);

                    line = br.readLine();
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        System.out.println("Examen JAVA");

        List<Persoana> persoaneFisier = citirePersoaneSQL();
//        persoaneFisier.forEach(System.out::println);

        long counterNerezidenti9 = persoaneFisier.stream().filter(persoana -> persoana.getCnp().startsWith("9")).count();
        long counterNerezidenti8 = persoaneFisier.stream().filter(persoana -> persoana.getCnp().startsWith("8")).count();
        long counterNerezidenti = counterNerezidenti9 + counterNerezidenti8;
        System.out.println("CERINTA 1: Numarul nerezidenti " + counterNerezidenti);
        System.out.println();

        List<Tranzactii> tranzactiiFisier = citireTXT();
//        tranzactiiFisier.forEach(System.out::println);

        Map<String, Long> simbolTranzactii = tranzactiiFisier.stream().collect(
                Collectors.groupingBy(
                        Tranzactii::getSimbol,
                        Collectors.counting()
                )
        );

        System.out.println("CERINTA 12: Numar tranzactii:");
        for (Map.Entry<String, Long> entry : simbolTranzactii.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " tranzactii");
        }
        System.out.println();

        // cerinta 3 - scriem simbolurile in fisier
        try (FileOutputStream fos = new FileOutputStream("simboluri.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);) {
                for(Map.Entry<String, Long> simbol : simbolTranzactii.entrySet()) {
                    bw.write(simbol.getKey() + System.lineSeparator());
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cerinta 4 - tranzactiile unui client
        System.out.println("CERINTA 4: Portofolii clienti");
        for(Persoana pers : persoaneFisier) {
            System.out.println("\t" + pers.getNume());
            Map<String, Integer> simbol_counter = tranzactiiFisier.stream().filter(tranzactii -> tranzactii.getCod() == pers.getCod()).collect(
                    Collectors.groupingBy(
                            Tranzactii::getSimbol,
                            Collectors.summingInt(Tranzactii::getCantitate)
                    )
            );
            for(Map.Entry<String, Integer> entry : simbol_counter.entrySet()) {
                System.out.println("\t\t" + entry.getKey() + " - " + entry.getValue());
            }
        }
    }
}