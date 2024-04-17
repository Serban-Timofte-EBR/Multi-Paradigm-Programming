import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
            Proiect p1 = new Proiect(121, "DB1001", "Baze de date",10000, 5);
            Proiect p2 = new Proiect(121, "DB1001", "Baze de date",10000, 5);
            Proiect p3 = new Proiect(2312, "JAVA21", "Programare", 2321, 2);

            System.out.println("p1 equals p2 " + p1.equals(p2));
            System.out.println("p2 equals p1 " + p2.equals(p1));
            System.out.println("p1 comparat cu p2 " +p1.compareTo(p2));
            System.out.println("p1 comparat cu p3 " + p1.compareTo(p3));
            System.out.println("p3 comparat cu p1 " + p3.compareTo(p1));

            List<Proiect> proiecteFisier = new ArrayList<>();

            try(FileInputStream fis = new FileInputStream("proiecte.csv");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)){
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    int cod = Integer.parseInt(values[0].trim());
                    double buget = Double.parseDouble(values[3].trim());
                    int nrPersoane = Integer.parseInt(values[4].trim());
                    Proiect proiectCitit = new Proiect(cod, values[1].trim(), values[2].trim(), buget, nrPersoane);
                    proiecteFisier.add(proiectCitit);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Proiectele din fisier sunt:");
            proiecteFisier.forEach(System.out::println);

            Map<String, List<String>> departament_acronime = proiecteFisier.stream().collect(Collectors.groupingBy(
                    Proiect::getDepartament,
                    Collectors.mapping(Proiect::getAcronim, Collectors.toList())
            ));

            departament_acronime.forEach((key, lista) -> {
                System.out.println(key + " " + lista);
            });

            System.out.println("--------------------");
            Map<String, List<Proiect>> departament_proiecte = proiecteFisier.stream().collect(Collectors.groupingBy(
                    Proiect::getDepartament
            ));

            departament_proiecte.entrySet().forEach(System.out::println);
            System.out.println("--------------------");
            List<Proiect> proiecteSortate = proiecteFisier.stream().sorted(new Comparator<Proiect>() {
                @Override
                public int compare(Proiect o1, Proiect o2) {
                    return Integer.compare(o2.getNumarMembrii(), o1.getNumarMembrii());
                }
            }).toList();

    //        proiecteSortate.forEach(System.out::println);

            try(FileOutputStream fos = new FileOutputStream("valoare_buget.csv");
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw)){
                for (Proiect proiect : proiecteSortate) {
                   String line = proiect.getCod() + "," + proiect.getAcronim() + "," + proiect.getBuget() + "," + proiect.getNumarMembrii();
                   bw.write(line + System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try(FileOutputStream fos = new FileOutputStream("proiecte.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                ) {
                for(Proiect proiect : proiecteSortate) {
                    oos.writeObject(proiect);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            List<Proiect> proiecteFisBin = new ArrayList<>();

            try (FileInputStream fis = new FileInputStream("proiecte.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)){
                while (true) {
                try {
                    Proiect proiectCitit = (Proiect) ois.readObject();
                    proiecteFisBin.add(proiectCitit);
                } catch (EOFException ex) {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            proiecteFisBin.forEach(System.out::println);

            Map<Integer, ?> ultimaCerinta = proiecteFisBin.stream().collect(Collectors.toMap(
                    Proiect::getCod,
                    proiect -> new Object(){
                        String acronim = proiect.getAcronim();
                        double buget = proiect.getBuget();

                        @Override
                        public String toString() {
                            return acronim + ", " + buget;
                        }
                    }
            ));

            ultimaCerinta.entrySet().forEach(System.out::println);
    }
}