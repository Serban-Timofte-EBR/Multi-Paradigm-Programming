/*
1. Să se definească o matrice bandă de tip real cu n linii și n coloane, iar lățimea de bandă (k1,k2).
Elementele de pe diagonala principală sunt inițializate cu 1, elementele de sub diagonala cu a iar
cele de deasupra diagonalei cu b. Valorile n, k1, k2, a și b sunt furnizate prin linie de comandă
(parametri metodei statice main).
2. Să se implementeze metode statice pentru adăugarea de noi linii/coloane la o matrice și
eliminarea ultimelor linii/coloane.
3. Să se calculeze și să se afișeze mediile pe grupe și media generală la disciplina Programare
multiparadigmă - Java. Notele vor fi citite dintr-un fișier text input.txt prin redirectarea inputului
standard. Va fi folosită clasa Scanner. Pe prima linie a fișierului se află n, numărul de grupe. Pe
următoarele n linii se află notele pe grupe. Pe fiecare linie, prima valoare este numărul grupei iar
următoarele valori, notele. Valorile sunt despărțite prin virgulă.
Exemplu. Pentru inputul:
2
1053,9,8,7,6,5,7,5,5,6,9
1052,7,7,6,5,5
outputul va fi:
1053,6.7
1052,6.0
Media generala:6.466666666666667
 */

package seminarJava.seminar1;

import java.util.Arrays;

//toate clasele mostenesc object
public class Main {
    public static void main(String[] args) {            //psvm
        System.out.println("Seminar 1 - 1064");                //sout
        //System - clasa utilitara
        //out - camp static
        for(String arg:args) {          //pentru fiecare string arg din args do {} - este ok si cu var (args - Collections cu mai multe stringuri - clasa in java)
            System.out.println(arg);
        }

        int n = Integer.parseInt(args[0].trim()); //trim elimina spatiile din stanga si dreapta
        double[][] x = new double[n][n];         // matricea - x pe stiva si matricea pe HEAP
        int k1 = Integer.parseInt(args[1].trim());
        int k2 = Integer.parseInt(args[2].trim());
        double a = Double.parseDouble(args[3].trim());
        double b = Double.parseDouble(args[4].trim());

        for (int i = 0; i < n; i++) {            //fori
            x[i][i] = 1;
            for (int j = Math.max(0, i-k1); j < i; j++) {
                x[i][j] = a;
            }

            for (int j = i + 1; j <= Math.min(n-1, i + k2); j++) {
                x[i][j] = b;
            }
        }

        afisareMatrice(x, "Matricea banda: ");

        double[] v = new double[x[0].length];

        for (int i = 0; i < n; i++) {
            v[i] = Math.random() * 100;
        }

        try {
            x = adaugareLinie(x, v);
            afisareMatrice(x, "Noua Matrice: ");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public static double[][] adaugareLinie(double[][] x, double[] v) throws Exception {
        int n = x.length, m = x[0].length;  //n - Linii si m - coloane
        if(v.length != m) {                // daca linia noua nu are dimensiunea buna
            throw new Exception("Dimensiuni incorecte pentru linia noua!");     //TREBUEI SA DECLAR CLASA CA ARUNCA EXCEPTII
        }

        double[][] y  = new double[n+1][m];     //daca declarat double[n+1][] trebuie sa declar in for pentru fiecare
        for (int i = 0; i < n; i++) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
        }

        System.arraycopy(v, 0, y[n], 0, v.length);

        return x;
    }

    private static void afisareMatrice(double[][] x, String mesaj){
        System.out.println(mesaj);
        for(double[] e:x) { //matricea este formata din vectori de double (functioneaza si varinta cu 2 for - uri ca in C++)
            System.out.println(Arrays.toString(e));
        }
    }
}
