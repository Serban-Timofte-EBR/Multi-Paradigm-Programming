import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

public class ServerFunctionare implements Runnable{
    private Socket client;

    public ServerFunctionare(Socket client) {
        this.client = client;
    }

    int generareRaspuns(int cod) {
        String url = "jdbc:sqlite:S07_admitere.db";
        String query = "SELECT NumarLocuri From Programe WHERE CodProgram = "  + cod;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(query))
        {
            if(res.next()) {
                return res.getInt("NumarLocuri");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
         try (InputStreamReader isr = new InputStreamReader(client.getInputStream());
              BufferedReader br = new BufferedReader(isr);
              PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
         {
                String codPrimit = br.readLine();
                int cod = Integer.parseInt(codPrimit);
                System.out.println("Serverul a primit codul " + codPrimit);

                int raspuns = generareRaspuns(cod);
                pw.println(raspuns);

         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }
}
