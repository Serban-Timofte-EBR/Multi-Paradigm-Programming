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

        try (Socket client = new Socket(host, port);
             InputStreamReader isr = new InputStreamReader(client.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            System.out.println("Introduceti codul unei sectii:");
            String cod = scanner.nextLine();

            pw.println(cod);

            String raspuns = br.readLine();
            System.out.println(raspuns);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
