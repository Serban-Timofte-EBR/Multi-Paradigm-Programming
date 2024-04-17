import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<MeteoData> meteoDataCititeDinFisier = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("date_meteo.csv");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double temp = Double.parseDouble(values[2].trim());
                int press = Integer.parseInt(values[3].trim());
                MeteoData dateCitite = new MeteoData(values[0].trim(), values[1].trim(), temp, press);
                meteoDataCititeDinFisier.add(dateCitite);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Date din fisier:");
        meteoDataCititeDinFisier.forEach(System.out::println);

        double temperaturaMedie = meteoDataCititeDinFisier.stream().collect(Collectors.averagingDouble(MeteoData::getTemperatura));
        System.out.println("Temperatura medie este " + temperaturaMedie);

        Optional<Double> temperaturaMinim = meteoDataCititeDinFisier.stream().map(MeteoData::getTemperatura).reduce(Double::min);
        System.out.println("Temperatura minima este " + temperaturaMinim.get());

        Optional<Double> temperaturaMaxima = meteoDataCititeDinFisier.stream().map(MeteoData::getTemperatura).reduce(Double::max);
        System.out.println("Temperatura maxima este " + temperaturaMaxima.get());

        Map<String, Map<String, String>> raport = meteoDataCititeDinFisier.stream().collect(Collectors.groupingBy(
                MeteoData::getOras,
                Collectors.groupingBy(
                        MeteoData::getData,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    double max = list.stream().mapToDouble(MeteoData::getTemperatura).max().orElse(Double.NaN);
                                    double min = list.stream().mapToDouble(MeteoData::getTemperatura).min().orElse(Double.NaN);
                                    return max + " " + min;
                                }
                        )
                )
        ));

        System.out.println(System.lineSeparator() + "Raport");
        raport.entrySet().forEach(System.out::println);

        List<MeteoData> datePeste18Grade = meteoDataCititeDinFisier.stream().filter(meteoData -> meteoData.getTemperatura() > 18).toList();

        try (FileOutputStream fos = new FileOutputStream("date_filtrare.csv");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)){
            for(MeteoData data : datePeste18Grade) {
                String line = data.getTemperatura() + "," + data.getOras() + "," +
                        data.getData() + "," + data.getPresiunea();
                bw.write(line + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}