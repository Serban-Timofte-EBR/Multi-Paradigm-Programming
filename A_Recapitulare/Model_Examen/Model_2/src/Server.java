import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static double valoareaPortofoliu(List<Tranzactii> tranzactii, int cod) {
        double valoare = tranzactii.stream().filter(tranzactie -> tranzactie.getCod() == cod)
                        .mapToDouble(tranzactie -> tranzactie.getPret() * tranzactie.getCantitate())
                        .sum();
        return valoare;
    }
    public static void main(String[] args) {
        int port = 12345;
        List<Persoana> persoane = Main.citirePersoaneSQL();
        List<Tranzactii> tranzactii = Main.citireTXT();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serverul a pornit");

                try (Socket client = server.accept();
                     InputStreamReader isr = new InputStreamReader(client.getInputStream());
                     BufferedReader br = new BufferedReader(isr);
                     PrintWriter pw = new PrintWriter(client.getOutputStream(), true)) {
                        String codPrimit = br.readLine();
                        int cod = Integer.parseInt(codPrimit);

                        double valoare = valoareaPortofoliu(tranzactii, cod);

                        pw.println(valoare);
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
