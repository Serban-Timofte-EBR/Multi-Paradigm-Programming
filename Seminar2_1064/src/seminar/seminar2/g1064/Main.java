package seminar.seminar2.g1064;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static List<Apartament> apartamente;
    public static List<Agent> agenti;
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        try {
            Apartament apartament = new Apartament(100, 90, 4, 4, "0789565432", Zona.AVIATIEI,
                    200000, fmt.parse("10.10.2023"), 1,
                    new String[]{"Parcare", "Centrala proprie"});
            System.out.println(apartament);
            Apartament apartament1 = new Apartament(100);
            System.out.println(apartament.equals(apartament1));

            Apartament clona = (Apartament) apartament.clone();
            apartament.getDotari()[0] = "fdgdsf";
            System.out.println("Obiect original:");
            System.out.println(apartament);
            System.out.println("Clona:");
            System.out.println(clona);

            citireDate("apartamente.csv");
            System.out.println("Lista de apartamente:");
            for (Apartament a : apartamente) {
                System.out.println(a);
            }
            System.out.println("Agenti:");
            for (Agent ag : agenti) {
                System.out.println(ag);
            }
            System.out.println("Lista apartamentelor sortata dupa pret:");
            Collections.sort(apartamente);
            for (Apartament a : apartamente) {
                System.out.println(a);
            }
            System.out.println("Lista apartamentelor sortata dupa data:");
            Collections.sort(apartamente, new Comparator<Apartament>() {
                @Override
                public int compare(Apartament apartament, Apartament t1) {
                    return apartament.dataP.compareTo(t1.dataP);
                }
            });
            for (Apartament a : apartamente) {
                System.out.println(a);
            }
            System.out.println("Lista apartamentelor sortata dupa telefon proprietar:");
            Collections.sort(apartamente, new Comparator<Apartament>() {
                @Override
                public int compare(Apartament apartament, Apartament t1) {
                    return apartament.telefonP.compareTo(t1.telefonP);
                }
            });
            for (Apartament a : apartamente) {
                System.out.println(a);
            }
            salvareAgenti("agenti.csv");
//            Cautare apartament dupa id
//            Apartament apartament2 = new Apartament(1000);
            Apartament apartament2 = new Apartament(4);
            int k = apartamente.indexOf(apartament2);
            if (k == -1) {
                System.out.println("Nu exista apartamentul cu id=" + apartament2.id);
            } else {
                System.out.println("Apartamentul cautat:\n" + apartamente.get(k));
            }
//            Cautare dupa telefon proprietar
            apartament2.setTelefonP("076654321");
            Comparator<Apartament> comparator = new Comparator<Apartament>() {
                @Override
                public int compare(Apartament apartament, Apartament t1) {
                    return apartament.telefonP.compareTo(t1.telefonP);
                }
            };
            Collections.sort(apartamente, comparator);
            k = Collections.binarySearch(apartamente, apartament2, comparator);
            if (k >= 0) {
                System.out.println("Apartamentul cautat:\n" + apartamente.get(k));
            } else {
                System.out.println("Nu a fost gasit apartamentul cu telefon prop. " + apartament2.getTelefonP());
                System.out.println("Pozitie de inserare:" + (-k - 1));
            }
//            salvare - serializare
            salvare("apartamente.dat");
            restaurare("apartamente.dat");
            System.out.println("Lista restaurata:");
            for (Apartament a:apartamente){
                System.out.println(a);
            }
            afisareCnpuri();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void afisareCnpuri(){
        Map<Integer,Long> cnpuri = new HashMap<>();
        for (Agent agent:agenti){
            for (int id:agent.getImobile()){
                cnpuri.put(id, agent.getCnp());
            }
        }
        System.out.println("Lista cnpuri:");
        for (int id: cnpuri.keySet()){
            System.out.println(id+":"+cnpuri.get(id));
        }
    }

    public static void restaurare(String numeFisier) {
        try (FileInputStream in1 = new FileInputStream(numeFisier);
             ObjectInputStream in = new ObjectInputStream(in1)) {
            apartamente.clear();
            while (in1.available()!=0){
                apartamente.add((Apartament) in.readObject());
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void salvare(String numeFisier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))) {
            for (Apartament apartament : apartamente) {
                out.writeObject(apartament);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void salvareAgenti(String numeFisier) {
        try (PrintWriter out = new PrintWriter(numeFisier)) {
            for (Agent agent : agenti) {
                out.println(agent.getCnp() + "," +
                        agent.getNume() + "," + agent.getImobile().length);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void citireDate(String numeFisier) {
        apartamente = new ArrayList<>();
        agenti = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(numeFisier))) {
            String linie;
            while ((linie = in.readLine()) != null) {
                String[] elemente = linie.split(",");
                Agent agent = new Agent(Long.parseLong(
                        elemente[0].trim()), elemente[1].trim());
                int n = Integer.parseInt(elemente[2].trim());
                for (int i = 0; i < n; i++) {
                    Apartament apartament = new Apartament();
                    elemente = in.readLine().split(",");
                    apartament.setId(Integer.parseInt(elemente[0].trim()));
                    apartament.setSuprafataUtila(Integer.parseInt(elemente[1].trim()));
                    apartament.setEtaje(Integer.parseInt(elemente[2].trim()));
                    apartament.setNrCamere(Integer.parseInt(elemente[3].trim()));
                    apartament.setTelefonP(elemente[4].trim());
                    apartament.setZona(Zona.valueOf(elemente[5].trim().toUpperCase()));
                    apartament.setPret(Double.parseDouble(elemente[6].trim()));
                    apartament.setDataP(fmt.parse(elemente[7].trim()));
                    apartament.setEtaj(Integer.parseInt(elemente[8].trim()));
                    String[] dotari = new String[elemente.length - 9];
                    for (int j = 0; j < dotari.length; j++) {
                        dotari[j] = elemente[9 + j];
                    }
                    apartament.setDotari(dotari);
                    apartamente.add(apartament);
                    agent.addImobil(apartament.id);
                }
                agenti.add(agent);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
