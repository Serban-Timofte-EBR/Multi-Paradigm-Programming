import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static List<Santier> citireSantiereJSON() {
        List<Santier> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("santiere.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int cod = obj.getInt("Cod Santier");
                String local = obj.getString("Localitate");
                String strada = obj.getString("Strada");
                String obiectiv = obj.getString("Obiectiv");
                Double val = obj.getDouble("Valoare");

                Santier santier = new Santier(cod,local,strada,obiectiv,val);
                lista.add(santier);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void scriereDevizeTXT(List<Capitol> capitole) {
        Map<Integer, List<Capitol>> cod_capitole = capitole.stream()
                .collect(Collectors.groupingBy(Capitol::getCodCapitol));

        try (FileOutputStream fos = new FileOutputStream("devize.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            for(Map.Entry<Integer, List<Capitol>> entry : cod_capitole.entrySet()) {
                bw.write(entry.getKey() + System.lineSeparator());
                for(Capitol capitol : entry.getValue()) {
                    String line = capitol.codSantier + ", " + capitol.cantitate * capitol.pretUnitar + System.lineSeparator();
                    bw.write(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Capitol> citireCapitoleDB() {
        List<Capitol> lista = new ArrayList<>();

        String url = "jdbc:sqlite:devize.db";
        String query = "SELECT * FROM CAPITOLE";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int codCapitol = res.getInt("cod_capitol");
                int codSantier = res.getInt("cod_santier");
                String denumire = res.getString("denumire");
                String um = res.getString("um");
                Double cant = res.getDouble("cantitate");
                Double pu = res.getDouble("pu");

                Capitol cap = new Capitol(codCapitol, codSantier, denumire, um, cant,pu);
                lista.add(cap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Santier> santiere = citireSantiereJSON();
        List<Capitol> capitole = citireCapitoleDB();

        System.out.println("CERINTA 1:" + System.lineSeparator());
        System.out.println("Santierele:");
        santiere.forEach(System.out::println);
        System.out.println();

        Double valMedie = santiere.stream().mapToDouble(Santier::getValoare).average().getAsDouble();
        System.out.println("Valoarea medie a santierelor este: " + valMedie);

        Map<Integer, Double> capitole_cantitati = capitole.stream().collect(
                Collectors.groupingBy(
                        Capitol::getCodCapitol,
                        Collectors.summingDouble(Capitol::getCantitate)
                ));

        System.out.println("CERINTA 2:");
        for(Map.Entry<Integer, Double> entry : capitole_cantitati.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println();

        System.out.println("CERINTA 3:");
        scriereDevizeTXT(capitole);
        System.out.println();
    }
}