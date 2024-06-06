import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static List<Program> citireProgramSQL() {
        List<Program> lista = new ArrayList<>();

        String url = "jdbc:sqlite:S07_admitere.db";
        String query = "SELECT * FROM Programe";

        try(Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query))
        {
            while (res.next()) {
                int codProgram = res.getInt("CodProgram");
                String codFacultate = res.getString("CodFacultate");
                String denumire = res.getString("Denumire");
                int nrLocuri = res.getInt("NumarLocuri");

                Program prog = new Program(codProgram, codFacultate,denumire,nrLocuri);
                lista.add(prog);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Candidat> citireCandidatiJSON() {
        List<Candidat> lista = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("S07_candidati.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsarr = new JSONArray(jstok);

            for(int i=0; i<jsarr.length(); i++) {
                JSONObject obj = jsarr.getJSONObject(i);
                String nume = obj.getString("nume");

                Map<Integer, Double> optiuni = new HashMap<>();
                JSONArray jsOptiuni = obj.getJSONArray("optiuni");
                for (int j = 0; j < jsOptiuni.length(); j++) {
                    JSONObject optiune = jsOptiuni.getJSONObject(j);
                    int codProgram = optiune.getInt("codProgram");
                    double punctaj = optiune.getDouble("punctaj");

                    optiuni.put(codProgram, punctaj);
                }

                Candidat cand = new Candidat(nume, optiuni);
                lista.add(cand);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Program> programe = citireProgramSQL();

        // Cerinta 1 - programele ordonate descrescator in functie de nr de locuri
        List<Program> programeDescrescator = programe.stream().sorted((p1, p2) -> Integer.compare(p2.getNrLocuri(), p1.getNrLocuri())).toList();
        System.out.println("CERINTA 1: Lista programe");
        for(Program program : programeDescrescator) {
            System.out.println(program.getCodProgram() + " " + program.getDenumire());
        }
        System.out.println();

        // Cerinta 2 - candidatii cu cel putin 3 optiuni sortat alfabetic
        List<Candidat> candidati = citireCandidatiJSON();
        List<Candidat> candidatiCu3OptiuniAlfabetic = candidati.stream().filter(cand -> cand.getOptiuni().size() > 2)
                .sorted((c1, c2) -> c1.getNume().compareTo(c2.getNume())).toList();
        System.out.println("CERINTA 2: Candidati cu cel putin 3 optiuni");
        for(Candidat cand : candidatiCu3OptiuniAlfabetic) {
            System.out.println(cand.getNume() + " - " + cand.getOptiuni().size() + " optiuni");
        }
        System.out.println();

        // Cerinta 3 - Lista de programe si numarul de candidati inscrisi
        System.out.println("CERINTA 3: Lista de programe:");
        for(Program program : programe) {
            int counter = 0;
            for(Candidat cand : candidati) {
                if(cand.getOptiuni().containsKey(program.getCodProgram())) {
                    counter++;
                }
            }
            System.out.println(program.getCodProgram() + " - " + program.getDenumire() + " - " + counter + " candidati");
        }
    }
}