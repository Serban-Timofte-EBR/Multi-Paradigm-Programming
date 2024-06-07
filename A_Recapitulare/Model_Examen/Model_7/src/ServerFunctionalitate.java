import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerFunctionalitate implements Runnable{
    private Socket client;

    public ServerFunctionalitate(Socket client) {
        this.client = client;
    }

    public static List<Angajat> citireAngajatiBinar() {
        List<Angajat> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("angajati.bin");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Angajat ang = (Angajat) ois.readObject();
                    lista.add(ang);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static String generareRaspuns(List<Angajat> lista, int cod) {
        Angajat ang = lista.stream().filter(angajat -> angajat.getId() == cod).findFirst().get();
        return "Nume: " + ang.getNume() + " - Salariul: " + ang.getSalariu();
    }
    @Override
    public void run() {
        List<Angajat> angajati = citireAngajatiBinar();

        try(InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true))
        {
            String mess = br.readLine();
            int cod = Integer.parseInt(mess);
            System.out.println("Serverul a primit codul: " + cod);

            String raspuns = generareRaspuns(angajati, cod);
            pw.println(raspuns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
