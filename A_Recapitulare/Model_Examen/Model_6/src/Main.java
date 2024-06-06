import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Specializare> citireSpecializariSQL() {
        List<Specializare> lista = new ArrayList<>();

        String url = "jdbc:sqlite:facultate.db";
        String query = "SELECT * FROM Specializari";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int cod = res.getInt("cod");
                String denumire = res.getString("denumire");
                int locuri = res.getInt("locuri");

                Specializare spec = new Specializare(cod,denumire,locuri);
                lista.add(spec);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static List<Inscriere> citireInscriereTXT() {
        List<Inscriere> lista = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("inscrieri.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");

                String cnp = values[0].trim();
                String nume = values[1].trim();
                Double notaBac = Double.parseDouble(values[2].trim());
                int codSpecializare = Integer.parseInt(values[3].trim());

                Inscriere inscriere = new Inscriere(cnp, nume, notaBac, codSpecializare);
                lista.add(inscriere);

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

    public static void main(String[] args) {
        List<Specializare> specializari = citireSpecializariSQL();
        List<Inscriere> inscrieri = citireInscriereTXT();

//        specializari.forEach(System.out::println);
//        inscrieri.forEach(System.out::println);

        // Cerinta 1 - Numarul total de locuri de la facultate
        Integer numarTotalDeLocuri = specializari.stream().mapToInt(spec -> spec.getLocuri()).sum();
        System.out.println("CERINTA 1:");
        System.out.println("La facultate sunt disponibile " + numarTotalDeLocuri + " locuri");
        System.out.println();

        // Cerinta 2 - Afisare specializari cu peste 100 de locuri disponibile dupa inscrieri;

        Map<Integer, Long> specilizari_inscrier = inscrieri.stream().collect(
                Collectors.groupingBy(
                        Inscriere::getCodSpecializare,
                        Collectors.counting()
                ));

        for(Specializare spec : specializari) {
            int nrLocuriActual = spec.getLocuri();
            long nrLocuriOcupate = specilizari_inscrier.get(spec.getCod());
            int noulNrDeLocuri = (int) (nrLocuriActual - nrLocuriOcupate);
            spec.setLocuri(noulNrDeLocuri);
        }

        System.out.println("CERINTA 2: Specializarile cu peste 100 de locuri dupa inscriere:");
        for(Specializare spec : specializari) {
            if(spec.getLocuri() >= 100) {
                System.out.println(spec.getCod() + " - " + spec.getDenumire() + " - " + spec.getLocuri() + " locuri disponibile");
            }
        }

        // Cerinta 3 - Scriere in JSON specializarile cu numarul de inscrier si media notelor la bac

        Map<Integer, Double> specializare_medieBac = inscrieri.stream().collect(
                Collectors.groupingBy(
                        Inscriere::getCodSpecializare,
                        Collectors.averagingDouble(Inscriere::getNotaBac)
                ));

        JSONArray jsonArray = new JSONArray();

        for(Specializare specializare : specializari) {
            long numarInscrisi = specilizari_inscrier.get(specializare.getCod());
            double media = specializare_medieBac.get(specializare.getCod());

            JSONObject obj = new JSONObject();
            obj.put("cod_specializare", specializare.getCod());
            obj.put("denumire", specializare.getDenumire());
            obj.put("numar_inscrier", numarInscrisi);
            obj.put("medie",media);

            jsonArray.put(obj);
        }

        try (FileWriter fw = new FileWriter("inscrieri_specializari.json")){
            fw.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}