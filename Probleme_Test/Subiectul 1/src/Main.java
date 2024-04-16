import classes.Element;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Element e1 = new Element(1, 0, 21.3);
        Element e2 = new Element(1, 0, 21.3);
        Element e3 = new Element(21, 21, 50);

        System.out.println("e1 comparat cu e3 " + e1.compareTo(e3));
        System.out.println("e3 comprata cu e1 " + e3.compareTo(e1));
        System.out.println("e1 comparat cu e2 " + e1.compareTo(e1));
        System.out.println("e1 == e2 " + e1.equals(e2));
        System.out.println("e1 == e3 " + e1.equals(e3));

        List<Element> elementeDinFisier = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream("matricerara.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int linie = Integer.parseInt(values[0].trim());
                int coloana = Integer.parseInt(values[1].trim());
                double val = Double.parseDouble(values[2].trim());
                Element elementCitit = new Element(linie, coloana, val);
                elementeDinFisier.add(elementCitit);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lista din fisier este: ");
        elementeDinFisier.forEach(System.out::println);

        long counterElementeNegative = elementeDinFisier.stream().filter(element -> element.getVal() < 0).count();
        System.out.println("Lista are " + counterElementeNegative + " elemente negative");

        Map<Integer, Double> mediiPeColoane = elementeDinFisier.stream().
                collect(Collectors.groupingBy(
                        Element::getColoana,
                        Collectors.averagingDouble(Element::getVal)
                        ));

        System.out.println("Medii pe coaloane");
        mediiPeColoane.forEach((coloana, medie) -> System.out.println(coloana + ": " + medie));

        try (FileOutputStream fos = new FileOutputStream("diagonal.dat");
            ObjectOutputStream dos = new ObjectOutputStream(fos))
        {
            for (Element element : elementeDinFisier) {
                if(element.getColoana() == element.getLinie()) {
                    dos.writeObject(element);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de elemente de pe diagonala principala se afla in diagonal.dat");

        try(FileInputStream fis = new FileInputStream("diagonal.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Element elementCitit;
            while (true)
            {
                try {
                    elementCitit = (Element) ois.readObject();
                    System.out.println(elementCitit);
                }
                catch (EOFException exception) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}