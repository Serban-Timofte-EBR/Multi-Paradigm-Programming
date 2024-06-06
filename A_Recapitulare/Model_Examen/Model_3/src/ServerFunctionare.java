import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerFunctionare implements Runnable {
    private Socket clientSocket;

    public ServerFunctionare(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static String cautaProprietarDupaNr(int nrApartament) {
        String url = "jdbc:sqlite:intretinere_apartamente.db";
        String query = "SELECT Nume FROM Apartamente WHERE NumarApartament = " + nrApartament;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query)) {

            if (res.next()) {
                return res.getString("Nume");
            } else {
                return "Apartament inexistent";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String numarPrimit = br.readLine();
            int numarApartament = Integer.parseInt(numarPrimit);
            System.out.println("Serverul a primit numarul de apartament " + numarApartament);

            String raspuns = cautaProprietarDupaNr(numarApartament);
            pw.println(raspuns);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
