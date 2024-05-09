package seminar.seminar10.g1065;

import seminar.seminar2.g1064.Agent;
import seminar.seminar2.g1064.Apartament;
import seminar.seminar2.g1064.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

public class MainServer implements AutoCloseable{
    private ServerSocket serverSocket;
    private boolean stopServer;

    public MainServer() throws IOException {
        serverSocket = new ServerSocket(2020);
    }

    public static void main(String[] args) {
        try (MainServer appServer = new MainServer()) {
            appServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws Exception {
        System.out.println("Start server ...");
        citireDate();
        serverSocket.setSoTimeout(5000);
        while (!stopServer) {
            try {
                Socket socket = serverSocket.accept();
                Thread firCerere = new Thread(() -> procesareCerere(socket));   // prin expresie lambda trecem codul functiei run
                firCerere.start();
            } catch (Exception exception) {}
        }
    }

    private void procesareCerere(Socket socket) {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
        ){
            String mesaj = ois.readObject().toString().toLowerCase();
            System.out.println("Mesajul primit de la server: " + mesaj);
            switch (mesaj) {
                case "stop":
                    stopServer = true;
                    System.out.println("Inchidere server ...");
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void citireDate() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:g1064.db")) {
            DatabaseMetaData dbm = connection.getMetaData();
            try (ResultSet rm = dbm.getTables(null, null, "APARTAMENTE",  new String[]{"TABLE"})) {
                if(!rm.next()) {
                    Main.citireDate("apartamente.csv");
                    List<Apartament> apartamente = Main.apartamente;
                    List<Agent> agenti = Main.agenti;
                    System.out.println(apartamente);
                    System.out.println(agenti);
                }
            }
        }
    }
    @Override
    public void close() throws Exception {
        if(serverSocket.isClosed()) {
            serverSocket.close();
            System.out.println("Server turning off ...");
        }
    }
}
