import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerHandler implements Runnable {
    Socket client;

    public ServerHandler(Socket client) {
        this.client = client;
    }

    // Să se implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:
        // Componenta client trimite serverului un cod de departament.
        // Componenta server va întoarce clientului denumirea și bugetul departamentului respectiv.
        // Componenta server poate servi oricâte cereri.

    public static String generareRaspuns(List<Departament> departamente, int cod) {
        Departament depatamentulCautat = departamente.stream()
                .filter(departament -> departament.getCodDepartament() == cod)
                .findFirst()
                .orElse(null);

        if(depatamentulCautat == null) {
            return "N|A";
        }

        String raspuns = "Denumire: " + depatamentulCautat.getDenumire() +
                " - Buget: " + depatamentulCautat.getBuget();

        return  raspuns;
    }

    @Override
    public void run() {
        List<Departament> departamente = Main.citireDepartamenteJSON();

        try(InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String codPrimit = br.readLine();
            int cod = Integer.parseInt(codPrimit);
            System.out.println("Serverul a primit codul: " + codPrimit);

            String raspuns = generareRaspuns(departamente, cod);
            pw.println(raspuns);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
