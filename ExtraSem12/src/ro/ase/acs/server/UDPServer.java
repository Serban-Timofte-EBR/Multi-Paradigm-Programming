package ro.ase.acs.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPServer extends Thread{

    private final int port = 7777;
    private DatagramSocket socket;

    public UDPServer() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started on port " + port);
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            byte[] buffer = new byte[256];
            // primim pachetul
            // instructiune blocanta pana la primirea pachetului
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                //lucram cu stringuri in comunicarea client - server
                String receivedMessage = new String(packet.getData());
                System.out.println(receivedMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //in try deschidem serverul UDP ( DATAGRAM SOCKET )
        UDPServer server = new UDPServer();
        try {
            // primul pachet il primim in afarat while true pentru a obtine adresa si portul clientului
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.socket.receive(packet);
            String receivedMessage = new String(packet.getData());
            System.out.println(receivedMessage);

            // preluam adresa si portul clientului pentru a raspunde
            InetAddress clientAdress = packet.getAddress();
            int clientPort = packet.getPort();

            // deschidem threadul pentru a primi mai multe mesaje
            server.start();

            while (true) {
                String message = scanner.nextLine();
                byte[] bytes = message.getBytes();
                DatagramPacket packetToBeSend = new DatagramPacket(bytes, bytes.length, clientAdress, clientPort);
                server.socket.send(packetToBeSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}
