import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerFunctionalitate implements Runnable{
    Socket client;

    public ServerFunctionalitate(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(int cod, List<Santier> santiere) {
        for(Santier santier : santiere) {
            if(santier.codSantier == cod) {
                String raspuns = santier.obiectiv + " - " + santier.valoare;
                return raspuns;
            }
        }
        return "N/A";
    }

    @Override
    public void run() {
        List<Santier> santiere = Main.citireSantiereJSON();

        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            int cod = Integer.parseInt(codPrimit);

            String raspuns = generareRaspuns(cod, santiere);
            pw.println(raspuns);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
