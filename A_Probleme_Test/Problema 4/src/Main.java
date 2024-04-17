import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Carte c1 = new Carte("Creanga", "Amintiri din Copilarie", "Traditional", 150);
        Carte c2 = new Carte("Calinescu", "Enigma Otiliei", "Realism", 354);

        System.out.println("Equals: " + c1.equals(c2));
        System.out.println("Compare to: " + c1.compareTo(c2));

        List<Carte> cartiDinFisier = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("carti.csv");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int nrPagini = Integer.parseInt(values[3].trim());
                Carte carteCitita = new Carte(values[0].trim(), values[1].trim(), values[2].trim(), nrPagini);
                cartiDinFisier.add(carteCitita);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cartile din fisier:");
        cartiDinFisier.forEach(System.out::println);

        Double averageNrPagini = cartiDinFisier.stream().collect(Collectors.averagingInt(Carte::getNrPagini));
        System.out.println("Numarul mediu de pagini din lista data este: " + averageNrPagini);

        //Stocare Gen -> Autori -> Lista de carti
        Map<String, List<String>> stil_titluri = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getStil,
                Collectors.mapping(Carte::getTitlul, Collectors.toList())
        ));

        System.out.println("Sorate Stil - Titluri:");
        stil_titluri.entrySet().forEach(System.out::println);

        Map<String, Map<String, List<String>>> autor_gen_titluri = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getAutor,
                Collectors.groupingBy(
                        Carte::getStil,
                        Collectors.mapping(Carte::getTitlul, Collectors.toList())
                )
        ));

        autor_gen_titluri.entrySet().forEach(System.out::println);

        List<Carte> cartiSortate_GenAutor = cartiDinFisier.stream().sorted(new Comparator<Carte>() {
            @Override
            public int compare(Carte o1, Carte o2) {
                return o1.getStil().compareTo(o2.getStil()) == 0 ? o1.getAutor().compareTo(o2.getAutor()) : o1.getStil().compareTo(o2.getStil());
            }
        }).toList();

//        cartiSortate_GenAutor.forEach(System.out::println);

        try(FileOutputStream fos = new FileOutputStream("carti_dupa_gen.csv");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)) {
            for (Carte carte : cartiSortate_GenAutor) {
                String line = carte.getStil() + "," + carte.getAutor() + "," +carte.getTitlul() + "," + carte.getNrPagini();
                bw.write(line + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String titlulCautat = "Autostrada Nebunilor";
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Introdu titlul pe care vrei sa il cauti: ");
//        titlulCautat = scanner.nextLine();
        List<Carte> cartiCuTitlulCautat = cartiDinFisier.stream().filter(carte -> carte.getTitlul().equals(titlulCautat)).toList();
        System.out.println("Cartile cu titlul cautat");
        cartiCuTitlulCautat.forEach(System.out::println);

//        Sortează cărțile după numărul de pagini în ordine descrescătoare și afișează primele 5 cărți din listă.
        List<Carte> cartiSortateDupaNumarulDePagini = cartiDinFisier.stream().sorted(new Comparator<Carte>() {
            @Override
            public int compare(Carte o1, Carte o2) {
                return Integer.compare(o2.getNrPagini(), o1.getNrPagini());
            }
        }).toList();
        System.out.println(System.lineSeparator() + "Carti sortate dupa numarul de pagini (Top 5)");
        for (int i = 0; i < 5; i++) {
            System.out.println(cartiSortateDupaNumarulDePagini.get(i));
        }

//        Filtrează cărțile care aparțin genului "Fantezie" și calculează suma totală a paginilor pentru acest gen.
        List<Carte> cartiFantezie = cartiDinFisier.stream().filter(
                carte -> carte.getStil().equals("Fantezie")
        ).toList();
        System.out.println(System.lineSeparator() + "Carti de Fantezie");
        cartiFantezie.forEach(System.out::println);
        long numarPagini = 0;
        for (Carte carte : cartiFantezie) {
            numarPagini += carte.getNrPagini();
        }
        System.out.println("Numarul total de pagini este " + numarPagini);

//        Identifică autorul cu cele mai multe cărți scrise și afișează numele autorului împreună cu numărul de cărți scrise.
        Map<String, Long> autori_Carti = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getAutor,
                Collectors.counting()
        ));

        Optional<Map.Entry<String, Long>> autor = autori_Carti.entrySet().stream().max(Map.Entry.comparingByValue());
        System.out.println(System.lineSeparator() + autor.get() + System.lineSeparator());

//        Gruparea cărților într-un map după gen, și apoi afișarea genului literar alături de numărul mediu de pagini pentru cărțile din acel gen.
        Map<String, Double> gen_nrPagini = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getStil,
                Collectors.averagingDouble(Carte::getNrPagini)
        ));
        System.out.println("Gen - Numar mediu de pagini");
        gen_nrPagini.entrySet().forEach(System.out::println);

//        Află titlurile cărții cu cel mai mare număr de pagini și a cărții cu cel mai mic număr de pagini din fiecare gen literar.
        Optional<Carte> titlu_numarPagini = cartiDinFisier.stream().max(new Comparator<Carte>() {
            @Override
            public int compare(Carte o1, Carte o2) {
                return Integer.compare(o1.getNrPagini(), o2.getNrPagini());
            }
        });

        System.out.println(System.lineSeparator() + "Titlul Cartii cu cel mai mare numar de pagini " + titlu_numarPagini.get().getAutor() + System.lineSeparator());

        Optional<Carte> titlu_minPagini = cartiDinFisier.stream().min(new Comparator<Carte>() {
            @Override
            public int compare(Carte o1, Carte o2) {
                return Integer.compare(o1.getNrPagini(), o2.getNrPagini());
            }
        });

        System.out.println(System.lineSeparator() + "Titlul Cartii cu cel mai mic numar de pagini " + titlu_minPagini.get().getAutor() + System.lineSeparator());

//        Calculează media, minima și maxima numărului de pagini pentru toate cărțile și afișează aceste statistici.
        IntSummaryStatistics statistics = cartiDinFisier.stream().collect(Collectors.summarizingInt(Carte::getNrPagini));
        System.out.println(System.lineSeparator() +
                "Medie: " + statistics.getAverage() +
                "\tMax: " + statistics.getMax() +
                "\tMin: " + statistics.getMin() +
                System.lineSeparator());

//        Filtrează și afișează toate cărțile care au numărul de pagini între 300 și 500 și care sunt din genul "SF" sau "Fantezie".
        List<Carte> cartiPaginiStil = cartiDinFisier.stream().filter(carte -> {
            return (carte.getNrPagini() > 300) && (carte.getNrPagini() < 500) &&
                    (carte.getStil().equals("SF") || carte.getStil().equals("Fantezie"));
        }).toList();

        System.out.println(System.lineSeparator() + "Cărțile care au numărul de pagini între 300 și 500 și care sunt din genul SF sau Fantezie" + System.lineSeparator());
        cartiPaginiStil.forEach(System.out::println);

//        Afișează toate cărțile într-un format citibil, unde fiecare carte este afișată astfel: "Titlul 'Numele Cărții' scrisă de Autorul X".

        System.out.println(System.lineSeparator() + "Lista in format citibil" + System.lineSeparator());
        cartiDinFisier.forEach(carte -> System.out.println("Titlul " + carte.getTitlul() +
                " scrisa de Autorul" + carte.getAutor()));

//        Să presupunem că dorim să grupăm cărți după genul literar și să obținem lista titlurilor pentru fiecare gen:
        Map<String, List<String>> carti_gen_titluri = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getStil,
                Collectors.mapping(Carte::getTitlul, Collectors.toList())
        ));

        System.out.println(System.lineSeparator() + "Să presupunem că dorim să grupăm cărți după genul literar și să obținem lista titlurilor pentru fiecare gen:" + System.lineSeparator());
        carti_gen_titluri.entrySet().forEach(System.out::println);

//        Să zicem că fiecare carte are un buget asociat și vrem să calculăm suma bugetelor pentru fiecare gen:
//        Map<String, Double> bugetTotalPerGen = carti.stream()
//                .collect(Collectors.groupingBy(
//                        Carte::getGen,
//                        Collectors.mapping(Carte::getBuget,
//                                Collectors.reducing(0.0, Double::sum))
//                ));

//        Fiecare carte are un număr specific de pagini, să calculăm media paginilor pentru fiecare gen:
        Map<String, Double> gen_nrMediuPagini = cartiDinFisier.stream().collect(Collectors.groupingBy(
                Carte::getStil,
                Collectors.averagingDouble(Carte::getNrPagini)
        ));
        System.out.println(System.lineSeparator() + "Media paginilor pentru fiecare gen" + System.lineSeparator());
        gen_nrMediuPagini.entrySet().forEach(System.out::println);
    }
}