import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Candidat> citireCandidatiJSON() {
        List<Candidat> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/candidati.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsarr = new JSONArray(jstok);

            for (int i = 0; i < jsarr.length(); i++) {
                JSONObject object = jsarr.getJSONObject(i);

                int codCandidat = object.getInt("cod_candidat");
                String numeCandidat = object.getString("nume_candidat");
                Double media = object.getDouble("media");

                JSONArray optiuni = object.getJSONArray("optiuni");
                Map<Integer, Integer> optiuniCandidat = new HashMap<>();
                for (int j = 0; j < optiuni.length(); j++) {
                    JSONObject optiune = optiuni.getJSONObject(j);

                    int codL = optiune.getInt("cod_liceu");
                    int codS = optiune.getInt("cod_specializare");

                    optiuniCandidat.put(codL, codS);
                }

                Candidat candidat = new Candidat(codCandidat, numeCandidat, media, optiuniCandidat);
                lista.add(candidat);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Liceu> citireLiceeTXT() {
        List<Liceu> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/licee.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values1[] = line.split(",");

                int codLiceu = Integer.parseInt(values1[0].trim());
                String numeLiceu = values1[1].trim();
                int nrOptiuni = Integer.parseInt(values1[2].trim());

                line = br.readLine();
                String values2[] = line.split(",");
                Map<Integer, Integer> specializari = new HashMap<>();
                for (int i = 0; i < nrOptiuni; i++) {
                    int index = i;
                    int codSpecializare = Integer.parseInt(values2[index].trim());
                    int nrLocrui = Integer.parseInt(values2[index + 1].trim());
                    specializari.put(codSpecializare, nrLocrui);
                }

                Liceu liceu = new Liceu(codLiceu, numeLiceu, nrOptiuni, specializari);
                lista.add(liceu);

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
        List<Candidat> candidati = citireCandidatiJSON();
        List<Liceu> licee = citireLiceeTXT();

        candidati.forEach(System.out::println);
        System.out.println();
        licee.forEach(System.out::println);
        System.out.println();

        Long counterCer1 = candidati.stream().filter(candidat -> candidat.getMedia() >= 9)
                        .count();
        System.out.println("CERINTA 1 - Numarul candidatilor cu medii mai mari sau egale cu 9 este: " + counterCer1);

        System.out.println("CERINTA 2 - Lista liceelor in functie de numarul de locuri:");
        licee.stream().sorted((l1, l2) -> Integer.compare(l2.counterLocuri(), l1.counterLocuri()))
                .forEach(liceu -> System.out.println(liceu.getCodLiceu() + " - " + liceu.getNumeLiceu()
                + " - " + liceu.counterLocuri()));
        System.out.println();

        System.out.println("CERINTA 3 - Candidatii dupa numarul de optiuni si medie (departajare)");
        List<Candidat> candidatiSortati = candidati.stream()
                .sorted(new Comparator<Candidat>() {
                    @Override
                    public int compare(Candidat o1, Candidat o2) {
                        int size1 = o1.getOptiuni().size();
                        int size2 = o2.getOptiuni().size();

                        if(size1 != size2) {
                            return Integer.compare(size2, size1);
                        } else {
                            return Double.compare(o2.getMedia(), o1.getMedia());
                        }
                    }
                }).toList();

        try (FileOutputStream fos = new FileOutputStream("jurnal.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            for(Candidat candidat : candidatiSortati) {
                String line = candidat.getCodCandidat() + ", " + candidat.getNumeCandidat() + ", " +
                        candidat.getOptiuni().size() + ", " + candidat.getMedia() + System.lineSeparator();
                bw.write(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:sqlite:examen.db";
        String query = "create table IF NOT EXISTS CANDIDATI (cod_candidat integer,nume_candidat text,medie double,numar_optiuni integer)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();)
        {
            stm.execute(query);

            for(Candidat candidat : candidati) {
                String queryInsert = "INSERT INTO CANDIDATI VALUES(" +
                        candidat.getCodCandidat() + ", " +
                        "'" + candidat.getNumeCandidat() + "', " +
                        candidat.getMedia() + ", " +
                        candidat.getOptiuni().size() + ")";

                stm.execute(queryInsert);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}