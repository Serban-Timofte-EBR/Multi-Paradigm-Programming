import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        List<Cititor> cititori = Main.citireCititoriJSON();

        try (ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul a fost pornit!");
            while (true) {
                Socket socket = server.accept();
                new Thread(new ServerFunctionalitate(socket, cititori)).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
