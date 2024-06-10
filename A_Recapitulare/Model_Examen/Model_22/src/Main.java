import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Sectie> citireSectiiJSON() {
        List<Sectie> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/sectii.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsarr = new JSONArray(jstok);

            for (int i = 0; i < jsarr.length(); i++) {
                JSONObject obj = jsarr.getJSONObject(i);

                int codSectie = obj.getInt("cod_sectie");
                String denumire = obj.getString("denumire");
                int nrLocuri = obj.getInt("numar_locuri");

                Sectie sectie = new Sectie(codSectie, denumire, nrLocuri);
                lista.add(sectie);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Pacient> citirePacientiTXT() {
        List<Pacient> lista = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("Date/pacienti.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");

                String cnp = values[0].trim();
                String nume = values[1].trim();
                int varsta = Integer.parseInt(values[2].trim());
                int codSectie = Integer.parseInt(values[3].trim());

                line = br.readLine();

                Pacient pacient = new Pacient(cnp, nume, varsta, codSectie);
                lista.add(pacient);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Sectie> sectii = citireSectiiJSON();
        List<Pacient> pacientii = citirePacientiTXT();

        sectii.forEach(System.out::println);
        System.out.println();
        pacientii.forEach(System.out::println);
        System.out.println();

        // Să afișeze la consolă lista secțiilor spitalului cu un număr de locuri strict mai mare decât 10
        System.out.println("CERINTA 1 - lista secțiilor spitalului cu un număr de locuri strict mai mare decât 10");
        sectii.stream().filter(sectie -> sectie.getNumarLocuri() > 10)
                .forEach(sectie -> System.out.println(sectie.getCodSectie() + ", " + sectie.getDenumire() + ", " + sectie.getNumarLocuri()));
        System.out.println();

        // Să afișeze la consolă lista secțiilor spitalului sortată descrescător după varsta medie a pacientilor internați pe secție
        Map<Integer, Double> codSectie_varstaMedie = pacientii.stream()
                .collect(Collectors.groupingBy(
                        Pacient::getSectie,
                        Collectors.averagingInt(Pacient::getVarsta)
                )).entrySet().stream().sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2)-> e2,
                        LinkedHashMap::new
                ));

        List<Sectie> sectiiLocuriLibere = sectii.stream()
                .sorted((s1, s2) -> codSectie_varstaMedie.getOrDefault(s2.getCodSectie(), Double.valueOf(0)).compareTo(codSectie_varstaMedie.getOrDefault(s1.getCodSectie(), Double.valueOf(0))))
                        .toList();
        System.out.println("CERINTA 2 - Sectiile sortate dupa varsta medie a pacientilor:");
        sectiiLocuriLibere.forEach(sectie -> System.out.println(
                sectie.getCodSectie() + ", " + sectie.getDenumire() + ", " + sectie.getNumarLocuri() + ", " + codSectie_varstaMedie.getOrDefault(sectie.getCodSectie(), Double.valueOf(0))
        ));
        System.out.println();

        System.out.println("CERINTA 3 - Jurnalul de internari");
        Map<Integer, Long> codSectie_nrPacienti = pacientii.stream()
                .collect(Collectors.groupingBy(
                        Pacient::getSectie,
                        Collectors.counting()
                ));

        List<Sectie> sectiiSortateDupaNrPacienti = sectii.stream()
                .sorted((s1, s2) -> Long.compare(codSectie_nrPacienti.getOrDefault(s2.getCodSectie(), 0L),
                        codSectie_nrPacienti.getOrDefault(s1.getCodSectie(), 0L)))
                .toList();

        //cod_secție_1,nume secție_1,numar_pacienti_1

        // scriere txt
        try (FileOutputStream fos = new FileOutputStream("jurnal.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)) {
            for (Sectie sectie : sectiiSortateDupaNrPacienti) {
                String line = sectie.getCodSectie() + ", " + sectie.getDenumire() + ", " + codSectie_nrPacienti.getOrDefault(sectie.getCodSectie(), 0L);
                bw.write(line + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // scriere JSON
        JSONArray jsonArray = new JSONArray();

        for(Sectie sectie : sectiiSortateDupaNrPacienti) {
            JSONObject object = new JSONObject();

            object.put("CodSectie", sectie.getCodSectie());
            object.put("DenumireSectie", sectie.getDenumire());
            object.put("NumarPacientiInternati", codSectie_nrPacienti.getOrDefault(sectie.getCodSectie(), 0L));

            jsonArray.put(object);
        }

        try(FileWriter fw = new FileWriter("jurnal.json")) {
            fw.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // scriere XML
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("sectii");
            doc.appendChild(root);

            for(Sectie sectie : sectiiSortateDupaNrPacienti) {
                Element elem = doc.createElement("sectie");

                elem.setAttribute("CodSectie", String.valueOf(sectie.getCodSectie()));
                elem.setAttribute("DenumireSectie", sectie.getDenumire());
                elem.setAttribute("NumarPacientiInternati", String.valueOf(codSectie_nrPacienti.getOrDefault(sectie.getCodSectie(), 0L)));

                root.appendChild(elem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File("jurnal.xml"));

            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        int port = 12345;

        try (ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            System.out.println("Serverul a primit codul: " + codPrimit);
            int cod = Integer.parseInt(codPrimit);

            int locuriSectie = sectii.stream().filter(sectie -> sectie.getCodSectie() == cod)
                    .findFirst().get().getNumarLocuri();
            long locuriOcupate = codSectie_nrPacienti.getOrDefault(cod, 0L);
            int locuriLibere = (int) (locuriSectie - locuriOcupate);

            pw.println("Numarul de locuri libere la sectia ceruta este: " + locuriLibere);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}