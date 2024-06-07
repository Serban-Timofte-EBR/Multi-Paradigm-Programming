import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serverul a fost pornit");

            Socket socket = server.accept();
            new Thread(new ServerFunctionalitate(socket)).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
