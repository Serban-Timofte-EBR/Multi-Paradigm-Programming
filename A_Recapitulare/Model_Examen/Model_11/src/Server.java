import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static String generareRaspuns(List<Curs> cursuri, List<Disciplina> discipline, String formatie) {
        List<Curs> cursuriFormatie = cursuri.stream()
                .filter(curs -> curs.getFormatie().equals(formatie))
                .toList();
        cursuriFormatie.forEach(System.out::println);
        String raspuns = "";
        for(Curs curs : cursuriFormatie) {
            Disciplina disciplina = discipline.stream().filter(disc -> disc.getCodDisciplina() == curs.getCodDisciplina())
                    .findFirst().get();
            raspuns = raspuns + " Zi: " + curs.getZi() + " Ora: " + curs.getInterval() +
                    " Sala: " + curs.getSala() + " Denumire: " + disciplina.getDenumire() + "\t\t\t";
        }
        return raspuns;

    }
    public static void main(String[] args) {
        int port = 12345;
        List<Curs> cursuri = Main.citireCursuriSQL();
        List<Disciplina> discipline = Main.citireDisciplineTXT();

        try(ServerSocket server = new ServerSocket(port))
        {
            System.out.println("Serverul a fost pornit!");
            try (Socket clinet = server.accept();
                 InputStreamReader isr = new InputStreamReader(clinet.getInputStream());
                 BufferedReader br = new BufferedReader(isr);
                 PrintWriter pw = new PrintWriter(clinet.getOutputStream(), true))
            {
                String formatie = br.readLine();
                System.out.println("Serverul a primit formatia: " + formatie);

                String raspuns = generareRaspuns(cursuri, discipline, formatie);
                pw.println(raspuns);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
