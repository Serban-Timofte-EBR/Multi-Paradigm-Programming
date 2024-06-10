import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerReqeustHandler implements Runnable{
    Socket client;

    public ServerReqeustHandler(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(int cod) {
        List<Rezervare> rezervari = Main.citireRezervariTXT();

        Map<Integer, Long> idRezervare_nrRezervari = rezervari.stream()
                .collect(Collectors.groupingBy(
                        Rezervare::getCodAventura,
                        Collectors.counting()
                ));

        return "Numarul de rezervari: " + idRezervare_nrRezervari.getOrDefault(cod, Long.valueOf(0));
    }

    @Override
    public void run() {
        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
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
