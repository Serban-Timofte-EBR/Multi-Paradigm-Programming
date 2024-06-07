import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerFunctionalitate implements Runnable {
    Socket client;

    public ServerFunctionalitate(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(List<Proiect> proiecte, int cod) {
        for(Proiect proiect : proiecte) {
            if(proiect.getCod() == cod) {
                String raspuns = "Numele: " + proiect.nume + " - Buget: " + proiect.buget;
                return raspuns;
            }
        }
        return "N|A";
    }

    @Override
    public void run() {
        List<Proiect> proiecte = Main.citireProiecteJSON();

        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            int cod = Integer.parseInt(codPrimit);
            System.out.println("Serverul a primit codul: " + codPrimit);

            String raspuns = generareRaspuns(proiecte, cod);
            pw.println(raspuns);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
