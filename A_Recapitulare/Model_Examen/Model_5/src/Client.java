import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 12345;
        String host = "localhost";
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                try (Socket socket = new Socket(host, port);
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);)
                {
                    System.out.println("Introduceti numele unui autor");
                    String nume = scanner.nextLine();

                    pw.println(nume);

                    String raspuns = br.readLine();
                    System.out.println(raspuns);
                }
                catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        finally {
            scanner.close();
        }
    }
}
