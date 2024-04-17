import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Carte> cartiFisier = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("carti.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int an = Integer.parseInt(values[3].trim());
                Carte carteFisier = new Carte(values[0].trim(), values[1].trim(), values[2].trim(), an);
                cartiFisier.add(carteFisier);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lista cartilor din fisier:");
        cartiFisier.forEach(System.out::println);

        List<Carte> cartiSorateDefault = cartiFisier.stream().sorted().toList();
        System.out.println(System.lineSeparator() + "Lista cartilor din fisier sortate dupa an (default)");
        cartiSorateDefault.forEach(System.out::println);

        Map<String, Long> raport_gen_counter = cartiFisier.stream().collect(Collectors.groupingBy(
                Carte::getGen,
                Collectors.counting()
        ));

        System.out.println(System.lineSeparator() + "Gen: Numar carti");
        raport_gen_counter.entrySet().forEach(System.out::println);

        Long cartiMaiVechiDe30Ani = cartiFisier.stream().filter(carte -> carte.getAn() < 1994).collect(Collectors.counting());
        System.out.println("Numarul cartilor mai vechi de 30 de ani este: " + cartiMaiVechiDe30Ani);

        Double anMediuDePublicare = cartiFisier.stream().collect(Collectors.averagingInt(Carte::getAn));
        System.out.println(System.lineSeparator() + "Anul mediu de publicare este: " + anMediuDePublicare.intValue());

        try(FileOutputStream fos = new FileOutputStream("cartiBinar.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            for(Carte c : cartiFisier) {
                oos.writeObject(c);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Carte> cartiBinare = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("cartiBinar.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Carte carteFisier = (Carte) ois.readObject();
                    cartiBinare.add(carteFisier);
                } catch (EOFException ex) {
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

        System.out.println(System.lineSeparator() + "Lista cartilor din fisierul binar este:");
        cartiBinare.forEach(System.out::println);

//        Calculați numărul total de cărți dintr-o listă de cărți
        long nrCarti = cartiBinare.stream().count();
        System.out.println(System.lineSeparator() + "Lista are urmatorul numar de carti: " + nrCarti);

//        Prima carte cu titlul citit de la tastatura
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdu titlul cautat: ");
        String titlu = scanner.nextLine();

        Carte carteaCautata = cartiBinare.stream().filter(carte -> carte.getTitlu().toLowerCase().equals(titlu.toLowerCase())).findFirst().get();
        System.out.println(System.lineSeparator() + "Prima carte cautata este: ");
        System.out.println(carteaCautata);

        System.out.println("Introdu autorul cautat: ");
        String autor = scanner.nextLine();
        boolean carteAutor = cartiBinare.stream().anyMatch(carte -> carte.getAutor().toLowerCase().equals(autor.toLowerCase()));
        if(carteAutor) {
            System.out.println("\tAutorul are carti in sistemul nostru");
            List<Carte> cartiAutor = cartiBinare.stream().filter(carte -> carte.getAutor().toLowerCase().equals(autor.toLowerCase())).toList();
            System.out.println("\tCartile autorului sunt:");
            cartiAutor.forEach(System.out::println);
        }
        else {
            System.out.println("Autorul nu are carti");
        }
        scanner.close();

//        Suma anilor
//        int sumaani = cartiBinare.stream().map(carte -> carte.getAn()).reduce(0, Integer::sum);
        double sumaani = cartiFisier.stream().mapToDouble(Carte::getAn).sum();
        System.out.println(System.lineSeparator() + "Suma anilor cartilor este " + (int)sumaani );

//        Cea mai veche carte
        Carte ceaMaiVecheCarte = cartiFisier.stream().min(Comparator.comparingInt(Carte::getAn)).get();
        System.out.println(System.lineSeparator() + "Cea mai veche carte este");
        System.out.println(ceaMaiVecheCarte);

        Carte ceaMaiNouaCarte = cartiFisier.stream().max(Comparator.comparingInt(Carte::getAn)).get();
        System.out.println(System.lineSeparator() + "Cea mai noua carte este");
        System.out.println(ceaMaiNouaCarte);
    }
}