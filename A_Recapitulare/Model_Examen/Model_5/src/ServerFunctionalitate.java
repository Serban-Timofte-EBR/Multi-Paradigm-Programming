import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServerFunctionalitate implements Runnable {
    private Socket client;
    private List<Cititor> cititori;

    public ServerFunctionalitate(Socket client, List<Cititor> cititori) {
        this.client = client;
        this.cititori = cititori;
    }

    public Long generareRaspuns(String nume) {
        Long counter = cititori.stream().filter(cititor -> cititor.getNume().equals(nume)).toList().stream().map(cititor -> cititor.getCartiImprumutate()).count();
        return counter;
    }

    @Override
    public void run() {
        try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String nume = br.readLine();
            System.out.println("Numele primit este " + nume);

            Long raspuns = generareRaspuns(nume);

            pw.println(raspuns);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
