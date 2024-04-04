package seminar.seminar6.g1064;

import org.w3c.dom.ls.LSOutput;
import seminar.seminar2.g1064.Agent;
import seminar.seminar2.g1064.Apartament;
import seminar.seminar2.g1064.Zona;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        seminar.seminar2.g1064.Main.citireDate("apartamente.csv");
        List<Apartament> apartamente = seminar.seminar2.g1064.Main.apartamente;
        System.out.println("Apartamente");
        for(var ap: apartamente) {
            System.out.println(ap);
        }
        System.out.println();

        apartamente.forEach(new Consumer<Apartament>() {
            @Override
            public void accept(Apartament apartament) {
                System.out.println(apartament);
            }
        });
        System.out.println();

        //SAU - acelasi efect

        apartamente.forEach(apartament -> System.out.println(apartament));

        System.out.println();

        apartamente.forEach(System.out::println);
        System.out.println();

        //Lista apartamentelor din Aviatie
        List<Apartament> lista_aviatiei = apartamente.stream()
                .filter(apartament -> apartament.getZona().equals(Zona.AVIATIEI))
                .toList();

        System.out.println("Apartamente din zona aviatiei");
        lista_aviatiei.forEach(System.out::println);

        System.out.println();

        // Selecția apartamentelor cu prețul cuprins între două valori specificate
        double pret_min = 80000;
        double pret_maxim = 150000;
        List<Apartament> lista_apartamente_preturi = apartamente.stream()
                .filter(apartament -> pret_min <= apartament.getPret() && apartament.getPret() >= pret_maxim)
                .toList();
        System.out.println("Apartamente in range de preturi");
        lista_apartamente_preturi.forEach(System.out::println);
        System.out.println();

        // Selecția apartamentelor care au loc de parcare
            // Vectorul de dotari este facut stream
        List<Apartament> apartamente_cu_loc_de_parcare = apartamente.stream()
                .filter(apartament -> Arrays.stream(apartament.getDotari())
                        .anyMatch(dotare -> dotare.toLowerCase().contains("parcare")))
                        .toList();
        System.out.println("Apartamente cu loc de parcare");
        apartamente_cu_loc_de_parcare.forEach(System.out::println);
        System.out.println();

        // Sortarea apartamentelor după valoare
            //sorted() face sortarea dupa compare()
        List<Apartament> lista_sortata = apartamente.stream().sorted().toList();
        System.out.println("Apartamente sortate dupa pret");
        apartamente.forEach(System.out::println);
        System.out.println();

        // Sortare dupa suprafata utila
        List<Apartament> lista_suprafata_utila = apartamente.stream()
                .sorted((apartament1, apartament2) -> Integer.compare(apartament2.getSuprafataUtila(), apartament1.getSuprafataUtila()))
                .toList();
        System.out.println("Apartamente dupa suprafata utila");
        lista_suprafata_utila.forEach(System.out::println);
        System.out.println();

        // Selecția tuturor numerelor de telefon ale proprietarilor pentru imobilele unui anumit agent într-
        // o colecție Set<String>. Pentru agent se va specifica cnp-ul.

        List<Agent> agenti = seminar.seminar2.g1064.Main.agenti;
        System.out.println("Lista agenti");
        agenti.forEach(System.out::println);
        long cnpAgent = 1680909234564L;

        Optional<Agent> lista = agenti.stream().filter(agent -> agent.getCnp() == cnpAgent).findFirst();
        if(lista.isPresent()) {
            Agent agent = lista.get();
            Set<String> numere_telefon = apartamente.stream()
                    .filter(apartament -> Arrays.stream(agent.getImobile()).anyMatch(id ->apartament.getId() == id))
                    .map(apartament -> apartament.getTelefonP())
                    .collect(Collectors.toSet());
            System.out.println("Lista nunerele de telefon");
            numere_telefon.forEach(System.out::println);
            //System.out.println(numere_telefon);
        }

        //Problema 6

        Map<Integer, String> cerinta6 = apartamente.stream()
                .collect(Collectors.toMap(Apartament::getId, Apartament::getTelefonP));
        System.out.println();
        System.out.println("Numarul de telefon pentru proprietarul fiecarui apartament");
        cerinta6.entrySet().forEach(System.out::println);

        //Problema 7 - V1
//        Map<Zona, List<Apartament>> apartamente_zone = apartamente.stream()
//                        .collect(Collectors.toMap(Apartament::getZona, apartament -> {
//                            List<Apartament> aparts = new ArrayList<>();
//                            aparts.add(apartament);
//                            return aparts;
//                        }));
//        apartamente_zone.entrySet().forEach(System.out::println);
        Map<Zona, List<Apartament>> apartamente_zone = apartamente.stream()
                .collect(Collectors.groupingBy(Apartament::getZona));
//        apartamente_zone.entrySet().forEach(System.out::println);
        System.out.println();
        System.out.println("Apartamente dupa zona: ");
        System.out.println();
        apartamente_zone.keySet().forEach(zona -> {
            System.out.println(zona);
            apartamente_zone.get(zona).forEach(System.out::println);
        });

        // Zona si id-ul
        Map<Zona, List<Integer>> id_zona = apartamente.stream()
                .collect(Collectors.groupingBy(Apartament::getZona,
                        Collectors.mapping(Apartament::getId, Collectors.toList())));
        System.out.println("\nLista Zona + Apartament ID\n");
        id_zona.entrySet().forEach(System.out::println);

        //SORTARE MAP - Suplimentar
        cerinta6.values().stream().sorted();
        cerinta6.entrySet().forEach(System.out::println);

        //Valori medii ale apartamentelor pe zone

        Map<Zona, Double> zona_valoare = apartamente.stream()
                .collect(Collectors.groupingBy(Apartament::getZona,
                        Collectors.averagingDouble(Apartament::getPret)));
        System.out.println("\nValoarea medie pe zone\n");
        zona_valoare.entrySet().forEach(System.out::println);

        // Map - Apartament si Valoarea (zona si pretul pe m^2)
        Map<Integer, ?> cerinta10 = apartamente.stream()
                .collect(Collectors.toMap(Apartament::getId,
                        apartament -> new Object(){
                            Zona zona = apartament.getZona();
                            Double pretm2 = apartament.getPret() / apartament.getSuprafataUtila();

                            @Override
                            public String toString() {
                                return zona + ", " + pretm2;
                            }
                        }));
        System.out.println("\nCerinta10\n");
        cerinta10.entrySet().forEach(System.out::println);

        //Numerele de telefon ale proprietarilor unui agent
        Map<Long, Set<String>> cerinta11 = agenti.stream()
                .collect(new Supplier<Map<Long, Set<String>>>() {
                    @Override
                    public Map<Long, Set<String>> get() {
                        return new HashMap<>();
                    }
                }, new BiConsumer<Map<Long, Set<String>>, Agent>() {
                    @Override
                    public void accept(Map<Long, Set<String>> longSetMap, Agent agent) {
                        Set<String> telefoane = new HashSet<>();
                        Arrays.stream(agent.getImobile()).forEach(id -> {
                            telefoane.add(apartamente.get(apartamente.indexOf(
                                    new Apartament(id)
                            )).getTelefonP());
                        });
                        longSetMap.put(agent.getCnp(), telefoane);
                    }
                }, new BiConsumer<Map<Long, Set<String>>, Map<Long, Set<String>>>() {
                    @Override
                    public void accept(Map<Long, Set<String>> longSetMap, Map<Long, Set<String>> longSetMap2) {
                        longSetMap.putAll(longSetMap2);
                    }
                });

        System.out.println("\nCerinta 11:\n");
        cerinta11.entrySet().forEach(System.out::println);
    }
}
