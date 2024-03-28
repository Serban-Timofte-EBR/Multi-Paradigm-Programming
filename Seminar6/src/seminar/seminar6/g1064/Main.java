package seminar.seminar6.g1064;

import seminar.seminar2.g1064.Agent;
import seminar.seminar2.g1064.Apartament;
import seminar.seminar2.g1064.Zona;

import java.util.*;
import java.util.function.Consumer;
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
    }
}
