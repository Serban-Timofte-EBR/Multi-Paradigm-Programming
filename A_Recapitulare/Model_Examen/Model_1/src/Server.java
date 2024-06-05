import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static List<Produs> produseFisier = new ArrayList<>();

    public static void main(String[] args) {

        // Refacem lista de produse din JSON
        try (FileInputStream fis = new FileInputStream("Produse.json");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            JSONTokener jstok = new JSONTokener(br);
            JSONArray jsa = new JSONArray(jstok);

            for (int i = 0; i < jsa.length(); i++) {
                JSONObject obj = jsa.getJSONObject(i);

                int cod = obj.getInt("Cod produs");
                String denumire = obj.getString("Denumire produs");

                Map<Integer, Double> consumuriProd = new HashMap<>();
                JSONArray consumuri = obj.getJSONArray("Consumuri");
                for (int j = 0; j < consumuri.length(); j++) {
                    JSONObject consum = consumuri.getJSONObject(j);

                    int codMateriePrima = consum.getInt("Cod materie prima");
                    double cant = consum.getDouble("Cantitate");

                    consumuriProd.put(codMateriePrima, cant);
                }

                int cantitate = obj.getInt("Cantitate");
                String unitateDeMasura = obj.getString("Unitate masura");

                Produs prod = new Produs(cod, denumire, consumuriProd, cantitate, unitateDeMasura);
                produseFisier.add(prod);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        produseFisier.forEach(System.out::println);


        // Creem serverul
        int port = 12345;

        try (ServerSocket server = new ServerSocket(port)){
            System.out.println("Serverul a fost pornit!");

            while (true) {
                try (Socket clientSocket = server.accept();
                    InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        String codProdus = br.readLine();
                        int cod = Integer.parseInt(codProdus);
                        System.out.println(cod);

                        int cantitatea = 0;
                        for(Produs produs : produseFisier) {
                            if(produs.getCodProdus() == cod) {
                                cantitatea = produs.getCantitate();
                            }
                        }

                        out.println(cantitatea);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
