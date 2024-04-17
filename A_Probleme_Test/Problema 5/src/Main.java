import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Film f1 = new Film("Inception",2010,"Sci-Fi",148);
        Film f2 = new Film("Inception",2010,"Sci-Fi",148);
        Film f3 = new Film("Interstellar",2014,"Sci-Fi",169);

        System.out.println("f1 comparat cu f2 " + f1.compareTo(f2));
        System.out.println("f1 comparat cu f3 " + f1.compareTo(f3));
        System.out.println("f3 comparat cu f1 " + f3.compareTo(f1));

        List<Film> filmeDinFisier = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("filme.csv");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int an = Integer.parseInt(values[1].trim());
                int durata = Integer.parseInt(values[3].trim());
                Film filmCitit = new Film(values[0].trim(), an, values[2].trim(), durata);
                filmeDinFisier.add(filmCitit);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lista filmelor din fisier:");
        filmeDinFisier.forEach(System.out::println);

        double durataMedie = filmeDinFisier.stream().collect(Collectors.averagingInt(Film::getDurata));
        System.out.println("Durata medie a filmelor din fisier este " + durataMedie + " minute");

        Map<String, List<String>> filmeGenTitluri = filmeDinFisier.stream().collect(Collectors.groupingBy(
                Film::getTip,
                Collectors.mapping(Film::getTitlu, Collectors.toList())
        ));

        System.out.println(System.lineSeparator());
        System.out.println("Filme grupate dupa gen");
        filmeGenTitluri.entrySet().forEach(System.out::println);

        List<Film> filmeDupaAn = filmeDinFisier.stream().sorted(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return Integer.compare(o1.getAn(), o2.getAn());
            }
        }).toList();

        System.out.println(System.lineSeparator());
        System.out.println("Lista filmelor dupa an:");
        filmeDupaAn.forEach(System.out::println);

        try (FileOutputStream fos = new FileOutputStream("filme_dupa_an.csv");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)){
            for (Film film : filmeDupaAn) {
                String line = film.getAn() + "," + film.getTitlu() + "," + film.getTip() + "," + film.getDurata() + System.lineSeparator();
                bw.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Film> filmeVechi = filmeDinFisier.stream().filter(film -> film.getAn() < 2004).toList();

        System.out.println(System.lineSeparator());
        System.out.println("Filmele mai vechi de 20 de ani: ");
        filmeVechi.forEach(System.out::println);


        Map<String, Map.Entry<Double, Double>> raportGenuri = filmeDinFisier.stream().collect(Collectors.groupingBy(
                Film::getTip,
                Collector.of(
                        () -> new double[2],
                        (acc, film) -> {
                            acc[0] += film.getDurata();
                            acc[1] += 1;
                        },
                        (acc1, acc2) -> {
                            acc1[0] += acc2[0];
                            acc1[1] += acc2[1];
                            return acc1;
                        },
                        acc -> new AbstractMap.SimpleEntry<>(
                                acc[1],
                                acc[0] / acc[1]
                        )
                )
        ));

        System.out.println("Raportul pe genuri");
        raportGenuri.entrySet().forEach(System.out::println);
    }
}