import seminar.seminar2.g1064.Agent;
import seminar.seminar2.g1064.Apartament;
import seminar.seminar2.g1064.Zona;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        seminar.seminar2.g1064.Main.citireDate("apartamente.csv");
        List<Agent> agenti = seminar.seminar2.g1064.Main.agenti;
        List<Apartament> apartamente = seminar.seminar2.g1064.Main.apartamente;

        apartamente.forEach(System.out::println);
        System.out.println();
        agenti.forEach(System.out::println);
        System.out.println();

        // apartamente din AVIATIEI
        List<Apartament> apartamenteAviatiei = apartamente.stream()
                .filter(apartament -> apartament.getZona().equals(Zona.AVIATIEI))
                .toList();
        System.out.println("CERINTA 1 - Apartamente din zona AVIATIEI:");
        apartamenteAviatiei.forEach(System.out::println);
        System.out.println();

        // apartamentele cu pretul intre doua preturi
        List<Apartament> apartamenteInRange = apartamente.stream()
                .filter(apartament -> apartament.getPret() < 200000 && apartament.getPret() > 100000)
                        .toList();
        System.out.println("CERINTA 2 - Apartamente in rangeul de pret");
        apartamenteInRange.forEach(System.out::println);
        System.out.println();

        // apartamente cu loc de parcare
        List<Apartament> apartamenteCuLocDeParcare = apartamente.stream()
                .filter(apartament -> Arrays.stream(apartament.getDotari()).filter(
                        dotare -> dotare.equals("loc parcare")
                ).count() == 1)
                .toList();
        System.out.println("CERINTA 3 - Apartamente cu loc de parcare:");
        apartamenteCuLocDeParcare.forEach(System.out::println);
        System.out.println();

        // apartamentele sortate dupa valoare
        List<Apartament> apartamenteDupaValoare = apartamente.stream()
                .sorted((a1, a2) -> Double.compare(a1.getPret(), a2.getPret()))
                .toList();
        System.out.println("CERINTA 4 - Apartamente sortate dupa valoare");
        apartamenteDupaValoare.forEach(System.out::println);
        System.out.println();

        // numarul de telefon a proprietarilor apartamentelor din lista unui agent
        long cnp = 1671010288599L;
        Agent agent = agenti.stream()
                        .filter(agent1 -> agent1.getCnp() == cnp)
                                .findFirst().orElse(null);
        if(agent != null) {
            int[] listaIDurilorApartamentelor = agent.getImobile();
            Set<String> numereDeTelefon = new HashSet<>();

            for(int id : listaIDurilorApartamentelor) {
                String numar = apartamente.stream().filter(apartament -> apartament.getId() == id)
                                .findFirst().get().getTelefonP();
                numereDeTelefon.add(numar);
            }

            System.out.println("CERINTA 5 - Numerele de telefon ale proprietarilor");
            numereDeTelefon.forEach(System.out::println);
            System.out.println();
        }

        // map cu id apartament si numarul de telefon
        Map<Integer, String> apartamentID_numarTelefon = apartamente.stream()
                .collect(Collectors.toMap(
                        Apartament::getId,
                        apartament -> apartament.getTelefonP()
                ));
        System.out.println("CERINTA 6 - Apartamentele si numerele de telefon ale proprietarilor");
        apartamentID_numarTelefon.entrySet().forEach(System.out::println);
        System.out.println();

        System.out.println("CERINTA 6.1 - Apartamentele de la cerinta 6 sortate");
        apartamentID_numarTelefon.entrySet().stream().sorted(Map.Entry.comparingByValue());
        apartamentID_numarTelefon.entrySet().forEach(System.out::println);
        System.out.println();

        // apartamentele grupate pe zone
        Map<Zona, List<Apartament>> apartamentePeZone = apartamente.stream()
                .collect(Collectors.groupingBy(
                        Apartament::getZona
                ));
        System.out.println("CERINTA 7 - Apartamentele pe zone");
        apartamentePeZone.entrySet().forEach(System.out::println);
        System.out.println();

        // apartamentele grupate pe zone si lista de id-uri
        Map<Zona, List<Integer>> zone_iduriApartamente = apartamente.stream()
                .collect(Collectors.groupingBy(
                        Apartament::getZona,
                        Collectors.mapping(apartament -> apartament.getId(), Collectors.toList())
                ));
        System.out.println("CERINTA 8 - ID-urile apartamentelor pe zone");
        zone_iduriApartamente.entrySet().forEach(System.out::println);
        System.out.println();

        // valoriile medii ale apartamentelor pe zone
        Map<Zona, Double> zona_valoareMedie = apartamente.stream()
                .collect(Collectors.groupingBy(
                        Apartament::getZona,
                        Collectors.averagingDouble(Apartament::getPret)
                ));
        System.out.println("CERINTA 9 - Valorea medie a apartamentelor:");
        zona_valoareMedie.entrySet().forEach(System.out::println);
        System.out.println();

        // Map<Integer, ?> dupa id (cheie) si zona si pret (valori)
        Map<Integer, ?> cerinta10 = apartamente.stream()
                .collect(Collectors.toMap(
                        Apartament::getId,
                        apartament -> new Object() {
                            Zona zona = apartament.getZona();
                            double pret = apartament.getPret();

                            @Override
                            public String toString() {
                                final StringBuilder sb = new StringBuilder("$classname{");
                                sb.append("zona=").append(zona);
                                sb.append(", pret=").append(pret);
                                sb.append('}');
                                return sb.toString();
                            }
                        }
                ));
        System.out.println("CERINTA 10 - ID-urile si detaliile:");
        cerinta10.entrySet().forEach(System.out::println);
        System.out.println();

        
    }
}