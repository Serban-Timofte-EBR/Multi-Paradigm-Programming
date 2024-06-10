import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Aventura> citireAventuriJSON() {
        List<Aventura> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/aventuri.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr))
        {
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int codAventura = object.getInt("cod_aventura");
                String denumire = object.getString("denumire");
                Double pret = object.getDouble("tarif");
                int locuriDisponibile = object.getInt("locuri_disponibile");

                Aventura aventura = new Aventura(codAventura, denumire, pret, locuriDisponibile);
                lista.add(aventura);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Rezervare> citireRezervariTXT() {
        List<Rezervare> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("Date/rezervari.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            String line = br.readLine();
            while (line != null) {
                String values[] = line.split(",");

                int idRezervare = Integer.parseInt(values[0].trim());
                int codAventura = Integer.parseInt(values[1].trim());
                int nrLocuriRezervare = Integer.parseInt(values[2].trim());

                line = br.readLine();

                Rezervare rezervare = new Rezervare(idRezervare, codAventura, nrLocuriRezervare);
                lista.add(rezervare);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void main(String[] args) {
        List<Aventura> aventuri = citireAventuriJSON();
        List<Rezervare> rezervari = citireRezervariTXT();

        aventuri.forEach(System.out::println);
        System.out.println();
        rezervari.forEach(System.out::println);
        System.out.println();

        System.out.println("CERINTA 1: Aventurile cu mai mult de 20 de locuri disponibile:");
        aventuri.stream().filter(aventura -> aventura.getLocuriDisponibile() >= 20)
                .forEach(aventura -> System.out.printf("Cod: %-5d Denumire: %-15s Tarif: %-3.2f \t Locuri disponibile: %d\n",
                        aventura.getCodAventura(), aventura.getDenumire(), aventura.getTarif(), aventura.getLocuriDisponibile()));
        System.out.println();

        System.out.println("CERINTA 2: Aventurile cu mai mult de 5 locuri dupa rezervari");
        Map<Integer, Integer> codAventura_locuriRezervate = rezervari.stream()
                .collect(Collectors.groupingBy(
                        Rezervare::getCodAventura,
                        Collectors.summingInt(Rezervare::getNumarLocuriRezevate)
                ));

        List<Aventura> aventuriLocuriDisponibile = aventuri.stream()
                .map(aventura -> {
                    int locuriActual = aventura.getLocuriDisponibile();
                    int locuriRezervate = codAventura_locuriRezervate.getOrDefault(aventura.getCodAventura(), 0);

                    return new Aventura(aventura.codAventura, aventura.getDenumire(), aventura.getTarif(),
                            locuriActual - locuriRezervate);
                }).toList();
        aventuriLocuriDisponibile.forEach(aventura ->
                System.out.println(aventura.getCodAventura() + ", " + aventura.getDenumire() + ", " + aventura.getLocuriDisponibile()));
        System.out.println();

        System.out.println("CERINTA 3 - Denumire aventura, Locuri rezervate, Valoare");
        try (FileOutputStream fis = new FileOutputStream("venituri.txt");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr))
        {
            for (Aventura aventura : aventuri) {
                int locuriRezervate = codAventura_locuriRezervate.getOrDefault(aventura.getCodAventura(), 0);
                double valoare = locuriRezervate * aventura.getTarif();
                String line = aventura.getDenumire() + ", " + locuriRezervate + ", " + valoare + System.lineSeparator();
                bw.write(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int port = 12345;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serverul a fost pornit!");
            while (true) {
                Socket client = server.accept();
                new Thread(new ServerReqeustHandler(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}