import java.util.Arrays;

public class Sala {
    private String[][] sala;
    private int locuriLibere;
    private String ultimulFir;
    public Sala(int[] l) {
        sala = new String[l.length][];
        for (int i = 0; i < sala.length; i++) {
            sala[i] = new String[l[i]];
            locuriLibere += l[i];
        }
    }

    public synchronized void rezervare(String nickname, int nrLocuri) {
        if(nrLocuri > locuriLibere) {return; }

        // ----------------Cerinta 2-----------------------
        String numeFir = Thread.currentThread().getName();
        if(ultimulFir != null) {
           while (numeFir.equals(ultimulFir) && Main.NR_FIRE > 1) {
               try {
                   wait();
               } catch (InterruptedException ex) {

               }
           }
        }
        ultimulFir = numeFir;
        System.out.println("Rezervare prin " + numeFir + " pentru " + nickname);
        //Vrem sa evitam sa avem Thread 1 - Thread 1 sau Thread 0 - Thread 0
        //---------------------------------------------------

        for (int i = 0, k = 0; i < sala.length && k <nrLocuri; i++) {
            for (int j = 0; j < sala[i].length && k <nrLocuri; j++) {
                if(sala[i][j] == null) {
                    sala[i][j] = nickname;
                    k++;
                    locuriLibere--;
                }
            }
        }
        notifyAll();
    }

    public void afisare() {
        for (String[] rand : sala) {
            System.out.println(Arrays.toString(rand));
        }
        System.out.println("Locuri Libere: " + locuriLibere);
    }
}
