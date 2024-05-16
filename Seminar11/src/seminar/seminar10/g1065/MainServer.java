package seminar.seminar10.g1065;

import seminar.seminar2.g1064.Agent;
import seminar.seminar2.g1064.Apartament;
import seminar.seminar2.g1064.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;

public class MainServer implements AutoCloseable{
    private ServerSocket serverSocket;
    private boolean stopServer;

    private List<Apartament> apartamente;
    private List<Agent> agenti;
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
                    System.out.println("Nu exista tabele!");
                    try(Statement sta = connection.createStatement()) {
                        String comandaCreare = "CREATE TABLE APARTAMENTE (" +
                                "id integer primary key,"+
                                "cnp_agent bigint,"+
                                "suprafata_utila interger,"+
                                "etaje integer,"+
                                "nr_camere integer,"+
                                "telefon_p varchar(20)," +
                                "zona varchar(20),"+
                                "pret double," +
                                "data_p varchar(10)," +
                                "etaj integer," +
                                "dotari varchar(50)"
                                + ")";
                        sta.executeUpdate(comandaCreare);
                        System.out.println("Tabela APARTAMENTE a fost creata!");
                        comandaCreare = "CREATE TABLE AGENTI (" +
                                "cnp bigint primary key," +
                                "nume varchar(30)" +
                                ")";
                        sta.executeUpdate(comandaCreare);
                        System.out.println("Tabela AGENTI a fost creata!");
                    }

                    System.out.println("Inserare articole");
                    Main.citireDate("apartamente.csv");
                    apartamente = Main.apartamente;
                    agenti = Main.agenti;
                    try(Statement sta = connection.createStatement()) {
                        for(Agent agent : agenti) {
                            String comandaInserare = "INSERT INTO AGENTI VALUES (" +
                                    agent.getCnp() + ", " +
                                    "'" + agent.getNume() + "'" +
                                    ")";
                            sta.executeUpdate(comandaInserare);
                            for (int id : agent.getImobile()) {
                                Apartament apartament = apartamente.stream().filter(ap -> ap.getId() == id).findFirst().get();
                                comandaInserare = "INSERT INTO APARTAMENTE VALUES (" +
                                        id + "," +
                                        agent.getCnp() + "," +
                                        apartament.getSuprafataUtila() + "," +
                                        apartament.getEtaje() + "," +
                                        apartament.getNrCamere() + "," +
                                        "'" + apartament.getTelefonP() + "'," +
                                        "'" + apartament.getZona() + "'," +
                                        apartament.getPret() + "," +
                                        "'" + Main.fmt.format(apartament.getDataP()) + "'," +
                                        apartament.getEtaj() + "," +
                                        "'" + String.join(",", apartament.getDotari()) + "'" +
                                        ")";
                                sta.executeUpdate(comandaInserare);
                            }
                        }
                    }
                    System.out.println("Inserari efectuate!");
                } else {
                    try(Statement sta = connection.createStatement();
                        ResultSet r = sta.executeQuery("SELECT  * FROM AGENTI")) {
                            while (r.next()) {
                                Agent agent = new Agent(r.getLong(1), r.getString(2));
                                agenti.add(agent);
                            }
                    }
                    try (Statement sta = connection.createStatement();
                        ResultSet r = sta.executeQuery("SELECT * FROM APARTAMENTE")) {
                            while (r.next()) {
                                Apartament apartament = new Apartament();
                                apartament.setId(r.getInt(1));
                                long cnp = r.getLong(2);
                                //// ...... ////
                                apartament.setDotari(r.getString(11).split(","));

                                Agent agent = agenti.stream().filter(ag -> ag.getCnp() == cnp).findFirst().get();
                                agent.addImobil(apartament.getId());
                            }
                    }
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
