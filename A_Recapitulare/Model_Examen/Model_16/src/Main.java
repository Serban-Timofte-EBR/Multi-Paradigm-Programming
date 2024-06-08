import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static List<Departament> citireDepartamenteSQL() {
        List<Departament> lista = new ArrayList<>();

        String url = "jdbc:sqlite:companie.db";
        String query = "SELECT * FROM Departamente";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int codDepartamente = res.getInt("codDepartament");
                String denumire = res.getString("denumire");
                Double buget = res.getDouble("buget");
                String manager = res.getString("manager");

                Departament departament = new Departament(codDepartamente, denumire, buget, manager);
                lista.add(departament);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Angajat> citireAngajatiTXT() {
        List<Angajat> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("angajati.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");
                int codAngajat = Integer.parseInt(values[0].trim());
                int codDepartament = Integer.parseInt(values[1].trim());
                String nume = values[2].trim();
                Double salariu = Double.parseDouble(values[3].trim());

                Angajat angajat = new Angajat(codAngajat, codDepartament, nume, salariu);
                lista.add(angajat);

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
        List<Departament> departamente = citireDepartamenteSQL();
        List<Angajat> angajati = citireAngajatiTXT();

        System.out.println("Departamentele:");
        departamente.forEach(System.out::println);
        System.out.println();

        System.out.println("Angajatii:");
        angajati.forEach(System.out::println);
        System.out.println();

        // Să se calculeze bugetul total al companiei (suma bugetelor tuturor departamentelor) și să se afișeze la consolă.
        Double bugetTotal = departamente.stream().collect(Collectors.summingDouble(Departament::getBuget));
        System.out.println("CERINTA 1 - Bugetul total:");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        System.out.println("Bugetul total al companiei este: " + decimalFormat.format(bugetTotal));

        // Să se afișeze la consolă numărul total de angajați pentru fiecare departament.
        Map<Integer, Long> departamente_numarAngajati = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodDepartament,
                        Collectors.counting()
                ));
        System.out.println();

        System.out.println("CERINTA 2 - Numarul de angajati din fiecare departament");
        for (Map.Entry<Integer, Long> entry : departamente_numarAngajati.entrySet()) {
            Departament departament = departamente.stream().filter(departament1 -> departament1.getCodDepartament() == entry.getKey())
                    .findFirst().get();
            System.out.printf("%-10s -   %d\n", departament.getNumire(), entry.getValue());
        }
        System.out.println();

        // Să se calculeze media salariilor tuturor angajaților și să se afișeze la consolă angajații care au salariul
        // mai mare decât această medie.

        Double salariulMediu = angajati.stream().collect(
                Collectors.averagingDouble(Angajat::getSalariul)
        );

        System.out.println("CERINTA 3 - Angajati cu salariul mai mare decat media:");
        System.out.println("Salariul mediu este: " + salariulMediu);
        System.out.println();
        System.out.println("Lista angajatilor cu salariul mai mare decat media:");
        angajati.stream().filter(angajat -> angajat.getSalariul() > salariulMediu)
                .forEach(angajat -> System.out.printf("%-10s - %5.2f\n", angajat.getNume(), angajat.getSalariul()));
        System.out.println();

        // Să se scrie într-un fișier departamente.json lista departamentelor și numărul de angajați din fiecare departament în format JSON.
        Map<Integer, Long> departament_nrAngajati = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodDepartament,
                        Collectors.counting()
                ));
        Map<Integer, Double> departament_salarii = angajati.stream()
                .collect(Collectors.groupingBy(
                        Angajat::getCodDepartament,
                        Collectors.summingDouble(Angajat::getSalariul)
                ));

        JSONArray jsonArray = new JSONArray();

        for(Map.Entry<Integer, Long> entry : departamente_numarAngajati.entrySet()) {
            Departament departament = departamente.stream()
                    .filter(departament1 -> departament1.getCodDepartament() == entry.getKey())
                    .findFirst().get();
            Double bugetSalarii = departament_salarii.getOrDefault(entry.getKey(), 0.0);
            Double bugetDisponibil = departament.getBuget() - bugetSalarii;

            JSONObject object = new JSONObject();
            object.put("CodDepartament", departament.getCodDepartament());
            object.put("DenumireDepartament", departament.getNumire());
            object.put("NumarAngajati", entry.getValue());
            object.put("BugetSalarii", bugetSalarii);
            object.put("BugetDisponibil", bugetDisponibil);

            jsonArray.put(object);
        }

        try (FileWriter fw = new FileWriter("detalii_departament.json")) {
            fw.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Să se implementeze un server și un client TCP/IP.
        // Clientul trimite serverului codul unui departament.
        // Serverul răspunde cu denumirea departamentului și bugetul acestuia.
        // Serverul poate servi oricâte cereri.

        int port = 12345;

        try(ServerSocket server = new ServerSocket(port);)
        {
            System.out.println("Serverul a pornit");
            while (true) {
                Socket client = server.accept();
                new Thread(new ServerRequestHandler(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}