import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Proiect> listaProiecte = new ArrayList<>();
        Map<Long, Double> sumeAdaugate = new HashMap<>();

        // Presupunem că listaProiecte și sumeAdaugate sunt deja populate cu date

        for (Proiect proiect : listaProiecte) {
            if (sumeAdaugate.containsKey(proiect.getCod())) {
                proiect.adaugaLaBuget(sumeAdaugate.get(proiect.getCod()));
            }
        }
    }
}