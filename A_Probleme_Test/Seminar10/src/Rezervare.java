import java.io.*;
import java.sql.ClientInfoStatus;

public class Rezervare implements Runnable{
    private String numeFisier;
    private Sala sala;

    public Rezervare(String numeFisier, Sala sala) {
        this.numeFisier = numeFisier;
        this.sala = sala;
    }

    @Override
    public void run() {
        Main.NR_FIRE++;
        try(FileInputStream fis = new FileInputStream(numeFisier);
            InputStreamReader dis = new InputStreamReader(fis);
            BufferedReader bw = new BufferedReader(dis)) {
            bw.lines().forEach(linie -> {
                String[] tok = linie.split(",");
                sala.rezervare(tok[0].trim(), Integer.parseInt(tok[1].trim()));
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.NR_FIRE--;
    }
}
