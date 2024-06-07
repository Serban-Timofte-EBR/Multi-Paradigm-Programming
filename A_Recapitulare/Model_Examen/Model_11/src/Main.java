import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Disciplina> citireDisciplineTXT() {
        List<Disciplina> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("S16_discipline.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");

                int cod = Integer.parseInt(values[0].trim());
                String denumire = values[1].trim();
                TIP tip = TIP.valueOf(values[2].trim());

                Disciplina disciplina = new Disciplina(cod, denumire, tip);
                lista.add(disciplina);

                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Curs> citireCursuriSQL() {
        List<Curs> lista = new ArrayList<>();

        String url = "jdbc:sqlite:S16_programari.db";
        String query = "SELECT * FROM Programari";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int cod = res.getInt("cod_disciplina");
                String zi = res.getString("zi");
                String interval = res.getString("interval");
                String sala = res.getString("sala");
                String formatie = res.getString("formatie");

                Curs curs = new Curs(cod, zi, interval, sala, formatie);
                lista.add(curs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void scriereProgramJSON(List<Disciplina> discipline, List<Curs> cursuri) {
        JSONArray jsonArray = new JSONArray();

        Map<String, List<Curs>> sali_cursuri = cursuri.stream()
                .collect(Collectors.groupingBy(Curs::getSala));
        for(Map.Entry<String, List<Curs>> entry : sali_cursuri.entrySet()) {
            if(entry.getKey().equals("A102")) {
                for(Curs curs: entry.getValue()) {
                    Disciplina disc = discipline.stream().filter(disciplina -> disciplina.getCodDisciplina() == curs.getCodDisciplina()).findFirst().get();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Zi", curs.getZi());
                    jsonObject.put("Ora", curs.getInterval());
                    jsonObject.put("Sala", curs.getSala());
                    jsonObject.put("Disciplina", disc.getDenumire());

                    jsonArray.put(jsonObject);
                }
            }
        }

        try(FileWriter fw = new FileWriter("program_sala.json")) {
            fw.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Disciplina> discipline = citireDisciplineTXT();
        List<Curs> cursuri = citireCursuriSQL();

        // discipline.forEach(System.out::println);
        // cursuri.forEach(System.out::println);

        // CERITNA 1 - Lista de discipline optionale
        List<Disciplina> disciplineOptionale = discipline.stream()
                .filter(disciplina -> disciplina.getTip().equals(TIP.optionala)).toList();

        System.out.println("CERINTA 1 - Discipline optionale:");
        for(Disciplina disciplina : disciplineOptionale) {
            System.out.println(disciplina.getDenumire());
        }
        System.out.println();

        // Cerinta 2 - Cursurile de vineri odonate crescator dupa sala
        List<Curs> cursuriVineriSoratate = cursuri.stream()
                .filter(curs -> curs.getZi().equals("Vineri"))
                .sorted((c1, c2) -> c1.getSala().compareTo(c2.getSala()))
                .toList();
        System.out.println("CERINTA 2 - Lista cursurilor programate vineri:");
        for(Curs curs : cursuriVineriSoratate) {
            Disciplina dis = discipline.stream()
                    .filter(disciplina -> disciplina.getCodDisciplina() == curs.getCodDisciplina())
                            .findFirst().get();
            System.out.printf("%5s, Ora %10s, %4s, %-30s\n", curs.getZi(), curs.getInterval(), curs.sala, dis.getDenumire());
        }
        System.out.println();

        // Cerinta 3 - Programul pentru o sala specificata
        Map<String, List<Curs>> sali_cursuri = cursuri.stream()
                .collect(Collectors.groupingBy(Curs::getSala));
        System.out.println("CERINTA 3 - Cursurile din sala A102:");
        for(Map.Entry<String, List<Curs>> entry : sali_cursuri.entrySet()) {
            if(entry.getKey().equals("A102")) {
                for(Curs curs: entry.getValue()) {
                    Disciplina disc = discipline.stream().filter(disciplina -> disciplina.getCodDisciplina() == curs.getCodDisciplina()).findFirst().get();
                    System.out.printf("%5s, Ora %10s, %4s, %-30s\n", curs.getZi(), curs.getInterval(), curs.getSala(), disc.getDenumire());
                }
            }
        }

        // Extra - Scriere programului salii intr-un fisier JSON
        scriereProgramJSON(discipline, cursuri);
    }
}