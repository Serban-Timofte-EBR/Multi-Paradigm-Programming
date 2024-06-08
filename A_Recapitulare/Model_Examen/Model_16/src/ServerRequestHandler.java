import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerRequestHandler implements Runnable {
    Socket client;

    public ServerRequestHandler(Socket client) {
        this.client = client;
    }

    // Să se implementeze un server și un client TCP/IP.
    // Clientul trimite serverului codul unui departament.
    // Serverul răspunde cu denumirea departamentului și bugetul acestuia.
    // Serverul poate servi oricâte cereri.

    public static String generareRaspuns(int cod) {
        List<Departament> departamente = Main.citireDepartamenteSQL();
        Departament departament = departamente.stream()
                .filter(departament1 -> departament1.getCodDepartament() == cod)
                .findFirst().orElse(null);

        if(departament == null) {
            return "Nu exista un departament cu codul cerut!";
        }

        return "Departament: " + departament.getNumire() + " - Buget: " + departament.getBuget();
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
