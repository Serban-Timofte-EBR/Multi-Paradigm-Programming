import javax.xml.crypto.Data;
import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static List<Apartament> apartamente;
    public static List<Agent> agenti;

    //declar un format pentru data in toata aplicatia
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
    public static void main(String[] args) {
        try {
            Apartament apart = new Apartament(100,90,4, 4, "0722222222",
                    Zona.DOROBANTI, 1000000, fmt.parse("10.10.2023"), 3,
                    new String[] {"Parcare", "Jacuzzi", "Wifi", "Apa calda", "Canalizare"});

            System.out.println(apart);

            Apartament apart1 = new Apartament(100);  //copiere a referintei
            System.out.println(apart == apart1);        //nu mai sunt aceleasi apartamente
            System.out.println(apart.equals(apart1));   //comparam daca sunt 2 apartamente cu acelasi ID

            Apartament clona = (Apartament) apart.clone();
            apart.getDotari()[0] = "sada";
            System.out.println("Obiect original: ");
            System.out.println(apart);
            System.out.println("Clona: ");
            System.out.println(clona);

            //apel citire din fisier
            citireDate("apartamente.csv");
            System.out.println("Apartamente: ");
            for (Apartament a:apartamente) {
                System.out.println(a);
            }

            System.out.println("Agenti: ");
            for (Agent ag:agenti) {
                System.out.println(ag);
            }

            //Sortare dupa compareto => pret
            System.out.println("Lista apartamentelor sortate dupa pret: ");
            Collections.sort(apartamente);
            for (Apartament a:apartamente) {
                System.out.println(a);
            }

            //Sortare dupa data publicarii
            System.out.println("Lista apartamentelor sortate dupa data: ");
            Collections.sort(apartamente, new Comparator<Apartament>() {
                @Override
                public int compare(Apartament o1, Apartament o2) {
                    return o1.dataP.compareTo(o1.dataP);
                }
            });
            for (Apartament a:apartamente) {
                System.out.println(a);
            }

            System.out.println("Sortare dupa telefon proprietar");
            Collections.sort(apartamente, new Comparator<Apartament>() {
                @Override
                public int compare(Apartament o1, Apartament o2) {
                    return o1.telefonP.compareTo(o2.telefonP);
                }
            });
            for (Apartament a:apartamente) {
                System.out.println(a);
            }

            int index = apartamente.indexOf(apart);
            System.out.println(index);
        }
        catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
    public static void citireDate(String nume_fisier) {
        apartamente = new ArrayList<>();
        agenti = new ArrayList<>();

        try(BufferedReader in = new BufferedReader(new FileReader(nume_fisier))) {
            String line;
            while( (line = in.readLine()) != null) {
                String[] elemente = line.split(",");
                Agent agent = new Agent(Long.parseLong(elemente[0].trim()), elemente[1].trim());
                int n = Integer.parseInt(elemente[2].trim());
                for (int i = 0; i < n; i++) {
                    Apartament apartament = new Apartament();
                    elemente = in.readLine().split(",");
                    apartament.setId(Integer.parseInt(elemente[0].trim()));
                    apartament.setSuprafataUtila(Integer.parseInt(elemente[1].trim()));
                    apartament.setEtaje(Integer.parseInt(elemente[2].trim()));
                    apartament.setNrClasa(Integer.parseInt(elemente[3].trim()));
                    apartament.setTelefonP(elemente[4].trim());
                    apartament.setZona(Zona.valueOf(elemente[5].trim()));   //conversier din String in Enum
                    apartament.setPret(Double.parseDouble(elemente[6].trim()));
                    apartament.setDataP(fmt.parse(elemente[7].trim()));
                    apartament.setEtaj(Integer.parseInt(elemente[8].trim()));
                    //numar variabil de dotari => citim de la etaj pana la finalul stringului
                    String[] dotari = new String[elemente.length - 9];
                    for (int j = 0; j < dotari.length; j++) {
                        dotari[j] = elemente[9 + j];
                    }
                    apartament.setDotari(dotari);
                    apartamente.add(apartament);
                    agent.addImobil(apartament.getId());
                }
                agenti.add(agent);
            }
        }

        catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}
