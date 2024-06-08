import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerHandlerRequests implements Runnable{
    Socket client;

    public ServerHandlerRequests(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(int cod) {
        List<Companie> companii = Main.citereCompaniiJSON();

        Companie companie = companii.stream()
                .filter(companie1 -> companie1.getCodCompanie() == cod)
                .findFirst().orElse(null);

        if(companie == null) {
            return "Nu exista o companie cu codul specificat!";
        }

        return "Denumire: " + companie.getDenumire() + "\t Cifra de afaceri: " + companie.getCifraDeAfaceri();
    }
    @Override
    public void run() {
        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            System.out.println("Codul primit este: " + codPrimit);
            int cod = Integer.parseInt(codPrimit);

            String raspuns = generareRaspuns(cod);
            pw.println(raspuns);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
