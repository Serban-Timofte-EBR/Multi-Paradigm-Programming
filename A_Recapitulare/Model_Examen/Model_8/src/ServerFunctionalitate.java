import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerFunctionalitate implements Runnable {
    private Socket client;

    public ServerFunctionalitate(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(List<Carte> lista, String cod) {
        for (Carte carte : lista) {
            if(carte.cotaCarte.equals(cod)) {
                String raspuns = "Titlu: " + carte.titlu + " Autor: " + carte.autor + " An: " + carte.anPublicare;
                return raspuns;
            }
        }
        return null;
    }

    @Override
    public void run() {
        List<Carte> carti = Main.citireCartiTXT();
        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String cota = br.readLine();
            System.out.println("Serverul a primit cota: " + cota);

            String raspuns = generareRaspuns(carti, cota);
            pw.println(raspuns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
