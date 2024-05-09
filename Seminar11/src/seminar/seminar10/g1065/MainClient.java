package seminar.seminar10.g1065;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainClient {
    private static final int STOP_CLIENT = 5;
    private static final int STOP_SERVER = 10;

    public static void main(String[] args) {
        try (BufferedReader cin = new BufferedReader(new InputStreamReader(System.in))) {
            int optiune;
            while ( (optiune = citesteOptiune(cin))!=STOP_CLIENT ) {
                switch (optiune) {
                    case STOP_SERVER:
                        stopServer();
                        break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void stopServer() {
        try(Socket socket = new Socket("localhost", 2020);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out.writeObject("stop");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static int citesteOptiune(BufferedReader cin) {
        int optiune = -1;
        System.out.println("1 - Returnarea unui apartament dupa id");
        System.out.println(STOP_CLIENT + " - Stop client");
        System.out.println(STOP_SERVER + " - Stop server");
        System.out.println("Optiune: ");
        try {
            optiune = Integer.parseInt(cin.readLine().trim());
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return optiune;
    }
}
