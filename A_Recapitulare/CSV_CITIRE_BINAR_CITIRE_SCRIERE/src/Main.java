import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // ----------------- Cerinta 1 - Lista de proiecte din fisier --------------------

        List<Proiect> proiecteFisier = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("P.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
                String line;
                line = br.readLine();
                while (line != null) {
                    String values[] = line.split(",");
                    Proiect pr = new Proiect(
                      Long.parseLong(values[0].trim()),
                      values[1].trim(),
                      Integer.parseInt(values[2].trim()),
                      values[3].trim(),
                      Double.parseDouble(values[4].trim()),
                      Integer.parseInt(values[5].trim()),
                      values[6].trim()
                    );
                    proiecteFisier.add(pr);
                    line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-->Cerinta 1");
        proiecteFisier.forEach(System.out::println);

        System.out.println();

        // ----------------- Cerinta 2 - Actualizarea cheltuielilor --------------------
        Map<Long, Double> cheltuieliFisier = new HashMap<Long, Double>();

        try(FileInputStream fis = new FileInputStream("Cheltuieli.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
                String line;
                line = br.readLine();
                while(line != null) {
                    String values[] = line.split(",");
                    long cod = Long.parseLong(values[0].trim());
                    double suma = Double.parseDouble(values[2].trim());
                    double sumaActuala = cheltuieliFisier.getOrDefault(cod, 0.0) + suma;

                    cheltuieliFisier.put(cod, sumaActuala);

                    line = br.readLine();
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println("Lista de cheltuieli:");
//        cheltuieliFisier.entrySet().forEach(System.out::println);

        for(Proiect pr:proiecteFisier) {
            pr.setCheltuieli(cheltuieliFisier.getOrDefault(pr.getCod(), 0.0));
        }

        System.out.println("--> Cerinta 2");
        proiecteFisier.forEach(System.out::println);

        System.out.println();
        // ----------------- Cerinta 3 - Salvarea listei de proiecte intr-un fisier binar --------------------

        try(FileOutputStream fos = new FileOutputStream("Proiecte.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                for(Proiect pr : proiecteFisier) {
                    oos.writeObject(pr);
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ----------------- Cerinta 4 - Restaurarea din fisier binar --------------------
        List<Proiect> proiecteFisierBinar = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream("Proiecte.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)){
                while (true) {
                    try {
                        Proiect pr = (Proiect) ois.readObject();
                        proiecteFisierBinar.add(pr);
                    } catch (EOFException ex) {
                        break;
                    }
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-->Cerinta 4");
        proiecteFisierBinar.forEach(System.out::println);

        System.out.println();

        // ----------------- Cerinta 5 - Sortarea proiectelor după bugetul disponibil --------------------

//        List<Proiect> listaSortata = proiecteFisierBinar.stream().sorted().toList();
        List<Proiect> listaSortata = proiecteFisierBinar.stream().sorted(Comparator.comparing(pr -> pr.getBuget() - pr.getCheltuieli())).toList();

        System.out.println("-->Cerinta 5");
        listaSortata.forEach(System.out::println);

        System.out.println();

        // ----------------- Cerinta 6 - Map<String, List<Proiect>> cu Departamente si proiecte --------------------
        Map<String, List<Proiect>> proiectePerDepartament = proiecteFisierBinar.stream().collect(
                Collectors.groupingBy(Proiect::getDepartment));

        System.out.println("-->Cerinta 6");
        proiectePerDepartament.entrySet().forEach(System.out::println);

        // V2 - Departament si ID-uri de proiect

        Map<String, List<Long>> cer6V2 = proiecteFisierBinar.stream().
                collect(Collectors.groupingBy(
                        Proiect::getDepartment,
                        Collectors.mapping(Proiect::getCod, Collectors.toList())
                ));

        System.out.println("-->Cerinta 6 -> Versiunea 2");
        cer6V2.entrySet().forEach(System.out::println);
    }
}