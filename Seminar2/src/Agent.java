import java.sql.Array;
import java.util.Arrays;

public class Agent implements Operatiuni {
    private long cnp;
    private String nume;
    private int[] imobile;      //Lucram separat cu int[], nu includem in constructor

    // Existenta constructorului default este ca in C++, daca este alt constructor nu mai este un constructor default
    public Agent() {
    }

    public Agent(long cnp, String nume) {
        this.cnp = cnp;
        this.nume = nume;
    }

    public Agent(long cnp) {
        this.cnp = cnp;
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int[] getImobile() {
        return imobile;
    }

    public void setImobile(int[] imobile) {
        this.imobile = imobile;
    }

    public void addImobil(int imobil) {
        if(this.imobile == null) {
            this.imobile = new int[1];
            this.imobile[0] = imobil;
        }
        else {
            int[] newImobile = new int[this.imobile.length + 1];
            System.arraycopy(this.imobile, 0, newImobile, 0, this.imobile.length);
            newImobile[this.imobile.length] = imobil;
            this.imobile = newImobile;
        }
    }

    @Override
    public String toString() {
        return "{" + cnp + ", " +  nume + ", "
                + Arrays.toString(imobile) + '}';   //fara toString il printa ca adresa
    }

    @Override
    public int inregistrare(int idImobil) {
        addImobil(idImobil);
        return this.imobile.length;
    }
}
