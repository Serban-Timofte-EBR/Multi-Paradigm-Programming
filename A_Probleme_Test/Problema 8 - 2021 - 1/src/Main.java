import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String fileName = "Balanta.csv";
        List<ContContabil> conturi1 = citesteConturi(fileName);
        afiseazaConturi(conturi1);

        String balantaFileName = "Balanta.csv";
        String noteContabileFileName = "NotaContabila.csv";
        List<ContContabil> conturi = citesteConturi(balantaFileName);
        actualizeazaRulaje(conturi, noteContabileFileName);
        afiseazaConturi(conturi);
    }

    public static List<ContContabil> citesteConturi(String fileName) {
        List<ContContabil> conturi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ContContabil cont = new ContContabil(
                        values[0], // simbol
                        values[1], // denumire
                        values[2].charAt(0), // tip
                        Double.parseDouble(values[3]), // soldInitial
                        Double.parseDouble(values[4]), // rulajDebitor
                        Double.parseDouble(values[5])  // rulajCreditor
                );
                conturi.add(cont);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conturi;
    }

    public static void afiseazaConturi(List<ContContabil> conturi) {
        for (ContContabil cont : conturi) {
            System.out.println(cont);
        }
    }

    public static void actualizeazaRulaje(List<ContContabil> conturi, String fileName) {
        Map<String, ContContabil> mapConturi = new HashMap<>();
        // Construim un map pentru a putea accesa ușor conturile după simbol
        for (ContContabil cont : conturi) {
            mapConturi.put(cont.getSimbol(), cont);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                NotaContabila nota = new NotaContabila(
                        values[0], // simbol
                        values[1].charAt(0), // tip
                        Double.parseDouble(values[2]) // suma
                );

                ContContabil cont = mapConturi.get(nota.getSimbol());
                if (cont != null) {
                    if (nota.getTip() == 'D') {
                        cont.setRulajDebitor(cont.getRulajDebitor() + nota.getSuma());
                    } else if (nota.getTip() == 'C') {
                        cont.setRulajCreditor(cont.getRulajCreditor() + nota.getSuma());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}