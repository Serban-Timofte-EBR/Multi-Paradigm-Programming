import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        List<Specializare> specializari = Main.citireSpecializariSQL();
        List<Inscriere> inscrieri = Main.citireInscriereTXT();
        Map<Integer, Long> specilizari_inscrier = inscrieri.stream().collect(
                Collectors.groupingBy(
                        Inscriere::getCodSpecializare,
                        Collectors.counting()
                ));

        for(Specializare spec : specializari) {
            int nrLocuriActual = spec.getLocuri();
            long nrLocuriOcupate = specilizari_inscrier.get(spec.getCod());
            int noulNrDeLocuri = (int) (nrLocuriActual - nrLocuriOcupate);
            spec.setLocuri(noulNrDeLocuri);
        }


        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serverul a pornit");

            try (Socket client = server.accept();
                 InputStreamReader isr = new InputStreamReader(client.getInputStream());
                 BufferedReader br = new BufferedReader(isr);
                 PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
            {
                String specializare = br.readLine();
                System.out.println("Specializarea primita: " + specializare);

                int nrLocuriActual = 0;
                for(Specializare spec : specializari) {
                    if(spec.getDenumire().equals(specializare)) {
                        nrLocuriActual = spec.getLocuri();
                    }
                }

                pw.println(nrLocuriActual);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
