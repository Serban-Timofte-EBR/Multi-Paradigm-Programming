import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Produs> citireProduseTXT() {
        List<Produs> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/Produse.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line = br.readLine();
            while (line != null){
                String values[] = line.split(",");
                int cod = Integer.parseInt(values[0].trim());
                String denumire = values[1].trim();
                double pret = Double.parseDouble(values[2].trim());

                Produs produs = new Produs(cod, denumire, pret);
                lista.add(produs);

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Tranzactie> citireTranzactiiJSON() {
        List<Tranzactie> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/Tranzactii.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsarr = new JSONArray(jstok);

            for (int i = 0; i < jsarr.length(); i++) {
                JSONObject obj = jsarr.getJSONObject(i);

                int codProdus = obj.getInt("codProdus");
                int cantitate = obj.getInt("cantitate");
                Tip tip = Tip.valueOf(obj.getString("tip"));

                Tranzactie tranzactie = new Tranzactie(codProdus, cantitate, tip);
                lista.add(tranzactie);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Produs> produse = citireProduseTXT();
        List<Tranzactie> tranzactii = citireTranzactiiJSON();

        produse.forEach(System.out::println);
        System.out.println();
        tranzactii.forEach(System.out::println);
        System.out.println();

        Long counter = produse.stream().count();
        System.out.println("CERINTA 1 - Numarul de produse este: " + counter);
        System.out.println();

        System.out.println("CERINTA 2 - Lista de produse ordonata alfabetic:");
        List<Produs> produseOrdonateAlfabetic =  produse.stream()
                .sorted((p1, p2) -> p1.getDenumire().compareTo(p2.getDenumire()))
                .toList();
        produseOrdonateAlfabetic.forEach(produs ->
                System.out.println(produs.getDenumire()));
        System.out.println();

        System.out.println("CERINTA 3 - Să se scrie în fișierul text date\\subiect1\\lista.txt un raport de forma:\n" +
                "Denumire Produs, Numar tranzactii");
        System.out.println();
        Map<String, Long> idProdus_nrTranzactii = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        tranzactie -> produse.stream().filter(produs -> produs.getCodProdus() == tranzactie.getCodProdus())
                                .findFirst().get().getDenumire(),
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1,e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e2,
                        LinkedHashMap::new
                ));

        try (FileOutputStream fos = new FileOutputStream("Date\\subiect1\\lista.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw))
        {
            bw.write("Denumire Produs, Numar tranzactii" + System.lineSeparator());
            for(Map.Entry<String, Long> entry : idProdus_nrTranzactii.entrySet()) {
                String line = entry.getKey() + "," + entry.getValue() + System.lineSeparator();
                bw.write(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Tranzactie> intrari = tranzactii.stream()
                .filter(tranzactie -> tranzactie.getTip().equals(Tip.intrare))
                .toList();

        Map<Integer, Double> codProdus_valoare = produse.stream()
                .collect(Collectors.toMap(
                        Produs::getCodProdus,
                        produs -> intrari.stream().filter(intrare -> intrare.getCodProdus() == produs.getCodProdus())
                                .mapToInt(intrare -> intrare.getCantitate()).sum() * produs.getPret()
                ));

        DecimalFormat format = new DecimalFormat("#.00");
        Double valoareaStocurilor = codProdus_valoare.entrySet().stream()
                        .mapToDouble(entry -> entry.getValue()).sum();
        System.out.println("CERINTA 4 - Valorile stocurilor este: " + valoareaStocurilor);
        System.out.println();

//        componenta client trimite serverului un cod de produs
//        iar componenta server va întoarce clientului valoarea stocului corespunzător

        int port = 12345;

        try(ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serverul a pornit");

            Socket client = server.accept();
            new Thread(new ServerRequestHandler(client)).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}