import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Punct p1 = new Punct("Figura 1", "Punctul 1", 10, 21);
        Punct p2 = new Punct("Figura 1", "Punctul 2", 10, 21);
        Punct p3 = new Punct("Figura 3", "Punctul 3", 5.5, 2.1);

        System.out.println("Comparam p1 cu p2 " + p1.compareTo(p2));
        System.out.println("Comparam p1 cu p3 " + p1.compareTo(p3));
        System.out.println("Comparam p3 cu p1 " + p3.compareTo(p1));

        List<Punct> puncteDinFisier = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("puncte.csv");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double x = Double.parseDouble(values[2].trim());
                double y = Double.parseDouble(values[3].trim());
                Punct punctCitit = new Punct(values[0].trim(), values[1].trim(), x, y);
                puncteDinFisier.add(punctCitit);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lista punctelor din fisier:");
        puncteDinFisier.forEach(System.out::println);

        long counterElemente = puncteDinFisier.stream().count();
        System.out.println("In fisier avem " + counterElemente + " elemente");

        Map<String, Long> eticheta_nrPuncte = puncteDinFisier.stream().collect(Collectors.groupingBy(
                Punct::getEtichetaFigura,
                Collectors.counting()
        ));

        System.out.println("Cerinta 3:");
        eticheta_nrPuncte.forEach((eticheta, nr) -> System.out.println(eticheta + ": " + nr));

        List<Distante> distante = new ArrayList<>();
        for (Punct punct : puncteDinFisier) {
            double distanta = punct.distanta();
            Distante d1 = new Distante(punct.getEtichetaFigura(), p2.getEtichetaPunctului(), distanta);
            distante.add(d1);
        }

        List<Distante> listaDeScrisInFisier = distante.stream().sorted().toList();
//        listaDeScrisInFisier.forEach(System.out::println);

        try(FileOutputStream fos = new FileOutputStream("distante.csv");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)) {
            for(Distante dist : listaDeScrisInFisier) {
                String lineToWrite = dist.getEtichetaFigura() + "," + dist.getEtichetaPunctului() + "," + dist.getDistanta();
                bw.write(lineToWrite + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}