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

            try(Socket socket = new Socket(host, port);
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);) {

                System.out.println("Introduceti codul persoanei cautaute: ");
                String cod = scanner.next();
                pw.println(cod);

                System.out.println(br.readLine());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
