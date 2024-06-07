import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Candidat> citireCandidatiJSON() {
        List<Candidat> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("S07_candidati.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String nume = obj.getString("nume");

                Map<Integer, Double> optiuniFisier = new HashMap<>();
                JSONArray optiuni = obj.getJSONArray("optiuni");
                for (int j = 0; j < optiuni.length(); j++) {
                    JSONObject optiune = optiuni.getJSONObject(j);
                    int codProgram = optiune.getInt("codProgram");
                    Double punctaj = optiune.getDouble("punctaj");

                    optiuniFisier.put(codProgram, punctaj);
                }

                Candidat candidat = new Candidat(nume, optiuniFisier);
                lista.add(candidat);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Program> citireProgrameXML() {
        List<Program> programe = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("S07_programe.xml"));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList nodeList = doc.getElementsByTagName("program");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);

                int codProgram = Integer.parseInt(elem.getElementsByTagName("codProgram").item(0).getTextContent());
                String denumire = elem.getElementsByTagName("denumire").item(0).getTextContent();
                int nrLocuri = Integer.parseInt(elem.getElementsByTagName("nrLocuri").item(0).getTextContent());

                Program program = new Program(codProgram, denumire, nrLocuri);
                programe.add(program);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return programe;
    }

    public static void inserareDB(List<Candidat> candidati) {
        String url = "jdbc:sqlite:admitere.db";
        String query = "CREATE TABLE IF NOT EXISTS OptiuniCandidati ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nume TEXT, " +
                "codProgram INTEGER, " +
                "punctaj REAL)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement())
        {
            stm.execute(query);

            for(Candidat candidat : candidati) {
                for(Map.Entry<Integer,Double> entry : candidat.getOptiuni().entrySet()) {
                    String queryInsert = "INSERT INTO OptiuniCandidati(nume, codProgram, punctaj) VALUES(" +
                            "'" + candidat.getNume() + "', " +
                            entry.getKey() + ", " + entry.getValue() + ")";

                    stm.execute(queryInsert);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Candidat> candidati = citireCandidatiJSON();
        List<Program> programe = citireProgrameXML();

        // candidati.forEach(System.out::println);
        // programe.forEach(System.out::println);

        System.out.println("CERINTA 1 - Numarul de optiuni a fiecarui student:");
        Map<String, Integer> candidat_nrOptiuni = candidati.stream()
                .collect(Collectors.toMap(
                        Candidat::getNume,
                        candidat -> candidat.getOptiuni().size()
                ));

        for(Map.Entry<String, Integer> entry : candidat_nrOptiuni.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " optiuni");
        }
        System.out.println();

        System.out.println("CERINTA 2 - Programele ordonate descrescator dupa numarul de locuri:");
        List<Program> programeOrdonateDesc = programe.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getNumarLocuri(), p1.getNumarLocuri()))
                .toList();
        for(Program program : programeOrdonateDesc) {
            System.out.println(program.getNumeProgram() + " - " + program.getNumarLocuri() + " locuri");
        }
        System.out.println();

        System.out.println("CERINTA 3 - Scriere in baza de date");
        inserareDB(candidati);
        System.out.println();

        System.out.println("CERINTA 5: Media punctajelor pentru fiecare program de studiu");
        Map<Integer, Double> codProgram_medie = candidati.stream()
                .flatMap(candidat -> candidat.getOptiuni().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.averagingDouble(Map.Entry::getValue)
                ));

        for (Map.Entry<Integer, Double> entry : codProgram_medie.entrySet()) {
            Program denumireProgram = programe.stream()
                    .filter(program -> program.getCodProgram() == entry.getKey())
                    .findFirst().orElse(null);

            if (denumireProgram != null) {
                System.out.println(denumireProgram.getNumeProgram() + " - " + entry.getValue());
            } else {
                System.out.println("Programul cu codul " + entry.getKey() + " nu a fost găsit.");
            }
        }

    }
}