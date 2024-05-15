package ro.ase.acs.client;

import ro.ase.acs.server.UDPServer;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient extends Thread{

    private DatagramSocket socket;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            // primim mesajul inapoi de la server
            byte[] buffer = new byte[256];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(receivedPacket);
                String receivedMessage = new String(receivedPacket.getData());
                System.out.println(receivedMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UDPClient client = new UDPClient();
        client.start();
        try {
            InetAddress serverAdress = InetAddress.getByName("localhost");
            int serverPort = 7777;
            while (true) {
                String message = scanner.nextLine();
                byte[] bytes = message.getBytes();

                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, serverAdress, serverPort);
                client.socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}
