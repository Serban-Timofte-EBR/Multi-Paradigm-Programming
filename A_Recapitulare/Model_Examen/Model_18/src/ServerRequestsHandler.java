import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerRequestsHandler implements Runnable {
    Socket client;

    public ServerRequestsHandler(Socket client) {
        this.client = client;
    }

    public static String generareRaspuns(int cod) {
        List<Vanzare> vanzari = Main.citireVanzariXML();
        List<Produs> produse = Main.citireProduseJSON();

        List<Vanzare> vanzariProdus = vanzari.stream()
                .filter(vanzare -> vanzare.getIdProdus() == cod)
                .toList();

        Produs produs = produse.stream()
                .filter(produs1 -> produs1.getId() == cod)
                .findFirst().orElse(null);

        if(produs != null){
            String raspuns = produs.getDenumire() + "\tComenzi: ";
            for(Vanzare vanzare : vanzariProdus) {
                raspuns += vanzare.getData() + " - " + vanzare.getCantitate() + "\t\t";
            }
            return raspuns;
        }

        return "Produsul nu a fost gasit!";
    }

    @Override
    public void run() {
        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(),true))
        {
            String idPrimit = br.readLine();
            System.out.println("Serverul a primit id-ul: " + idPrimit);
            int id = Integer.parseInt(idPrimit);

            String raspuns = generareRaspuns(id);
            pw.println(raspuns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
