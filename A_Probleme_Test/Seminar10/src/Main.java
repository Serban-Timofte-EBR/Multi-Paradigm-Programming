/*
Să se scrie o aplicație de tip rezervare locuri într-o sală de spectacole. Sala de spectacole va fi
reprezentată printr-un obiect dintr-o clasă Sala care va conține un masiv bidimensional de tip
String[][] pentru memorarea locurilor. Numărul de linii din masiv va fi numărul de rânduri din
sală, iar numărul de elemente de pe o linie în masiv va reprezenta numărul de locuri de pe rândul
corespunzător în sală. Rândurile pot avea un număr diferit de locuri. Cererile de rezervare vor fi
preluate din fișiere text diferite, câte o rezervare pe un rând. Structura unui fișier va fi:
nume_1,nr_locuri_1
nume_2,nr_locuri_2
...
unde nume_x reprezintă numele solicitantului iar nr_locuri_x reprezintă numărul de locuri
solicitate.
Aplicația va marca un loc ocupat în masivul sală cu numele solicitantului. Pentru fiecare fișier se
va crea un fir de execuție.
Cerințe:
1. Aplicația trebuie să asigure alocarea în consecutivitate a locurilor dintr-o cerere
2. Utilizând unul din mecanismele de sincronizare (wait-notify, blocuri sincrone, semafoare) să
se evite efectuarea a două rezervări consecutive din același fișier
*/

import java.io.File;

public class Main {
    public static int NR_FIRE;
    public static void main(String[] args) {
        int l[]= {10, 12, 9, 8, 10, 8, 7}; //nr locuri pe rand ( randul este indexul, iar valoarea este nr de randuri )
        Sala sala = new Sala(l);
        sala.rezervare("Mihai", 4);
        sala.afisare();

        //citim rezervarile din fisier
        String[] fisiere = new File(".").list(
                (director, numeFisier) -> {
                   return numeFisier.endsWith(".csv");
                });

        Thread[] fireRezervare = new Thread[fisiere.length];
        for (int i = 0; i < fireRezervare.length; i++) {
            fireRezervare[i] = new Thread(new Rezervare(fisiere[i], sala));
        }

        for (int i = 0; i < fireRezervare.length; i++) {
            fireRezervare[i].start();
        }

        for (int i = 0; i < fireRezervare.length; i++) {
            try {
                fireRezervare[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println();
        sala.afisare();
    }
}