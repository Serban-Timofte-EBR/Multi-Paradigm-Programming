import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    public static List<Factura> citireFacturiTXT() {
        List<Factura> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("intretinere_facturi.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");
                String denumire = values[0].trim();
                String tip = values[1].trim();
                double valoare = Double.parseDouble(values[2].trim());

                Factura fac = new Factura(denumire, Tip.valueOf(tip), valoare);
                lista.add(fac);

                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Apartament> citireApartamenteSQL() {
        List<Apartament> lista = new ArrayList<>();

        String url = "jdbc:sqlite:intretinere_apartamente.db";
        String query = "SELECT * FROM Apartamente";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query)) {

            while (res.next()) {
                int numarApartament = res.getInt("NumarApartament");
                String nume = res.getString("Nume");
                int suprafata = res.getInt("Suprafata");
                int numarPersoane = res.getInt("NumarPersoane");

                Apartament apart = new Apartament(numarApartament, nume, suprafata, numarPersoane);
                lista.add(apart);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Factura> facturi = citireFacturiTXT();
//        facturi.forEach(System.out::println);

        Map<Tip, Long> facturiPerRepartizare = facturi.stream().filter(factura -> factura.getRepartizare().equals(Tip.suprafata) || factura.getRepartizare().equals(Tip.persoane))
                .collect(
                Collectors.groupingBy(
                        Factura::getRepartizare,
                        Collectors.counting()
                ));

        // Numarul de facturi in functie de tip

        System.out.println("CERINTA 1:");
        for(Map.Entry<Tip, Long> entry : facturiPerRepartizare.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " facturi");
        }


        // Valoarea totala a facturilor in functie de tip

        System.out.println(System.lineSeparator() + "CERINTA 2:");
        Map<Tip, Double> tipValoare = facturi.stream().collect(
                Collectors.groupingBy(
                        Factura::getRepartizare,
                        Collectors.summingDouble(Factura::getValoare)
                ));

        for(Map.Entry<Tip, Double> entry : tipValoare.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " RON");
        }
        System.out.println();

        // Suprafata totala a apartamentelor din bloc

        List<Apartament> apartamente = citireApartamenteSQL();
//        apartamente.forEach(System.out::println);

        Integer suprafataTotala  = apartamente.stream().mapToInt(apartament -> apartament.getSuprafata()).sum();
        System.out.println("CERINTA 3:");
        System.out.println("Suprafata totala a apartamentelor din bloc este " + suprafataTotala);

        // Cerinta 4: Tabela de intretinere - pentru fiecare apartament aplic in functie de tipul facturii
        System.out.println("CERINTA 4");
        System.out.println("Numar apartament, Nume, Suprafata, Persoane, Total de plata");
        for(Apartament apart : apartamente) {
            double valoareDePlata = 0.0;
            for(Factura factura : facturi) {
                if(factura.getRepartizare().equals(Tip.persoane)) {
                    valoareDePlata += factura.getValoare() * apart.getNumarPersoane();
                }
                if(factura.getRepartizare().equals(Tip.suprafata)) {
                    valoareDePlata += factura.getValoare() * apart.getSuprafata();
                }
                if (factura.getRepartizare().equals(Tip.apartament)) {
                    valoareDePlata += factura.getValoare();
                }
            }
            System.out.println(apart.getNumarApartament() + " " + apart.getNume() + " " + apart.getSuprafata() + " " +
                    apart.getNumarPersoane() + " " + valoareDePlata);
        }
    }
}