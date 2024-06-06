import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul a pornit");

            while (true) {
                Socket client = server.accept();
                new Thread(new ServerFunctionare(client)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
