import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerRequestHandler implements Runnable{
    Socket client;

    public ServerRequestHandler(Socket client) {
        this.client = client;
    }

    //        componenta client trimite serverului un cod de produs
//        iar componenta server va întoarce clientului valoarea stocului corespunzător

    public static String generareRaspuns(int cod) {
        List<Tranzactie> tranzactii = Main.citireTranzactiiJSON();
        List<Produs> produse = Main.citireProduseTXT();

        Map<Integer, Integer> codProdus_cantitate = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        Tranzactie::getCodProdus,
                        Collectors.summingInt(Tranzactie::getCantitate)
                ));

        Map<Integer, Double> codProdus_valoare = new HashMap<>();
        for(Map.Entry<Integer, Integer> entry : codProdus_cantitate.entrySet()) {
            Produs produs = produse.stream().filter(produs1 -> produs1.getCodProdus() == entry.getKey())
                    .findFirst().get();
            Double valoare = produs.getPret() * entry.getValue();
            codProdus_valoare.put(entry.getKey(), valoare);
        }

        String raspuns = "Valoarea: " + codProdus_valoare.get(cod);
        return raspuns;
    }
    @Override
    public void run() {
        try(InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            System.out.println("Serverul a primit codul: " + codPrimit);
            int cod = Integer.parseInt(codPrimit);

            String raspuns = generareRaspuns(cod);
            pw.println(raspuns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
