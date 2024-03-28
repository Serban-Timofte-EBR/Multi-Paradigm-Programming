package seminar.seminar2.g1064;

import java.util.Arrays;

public class Agent implements Operatiuni {
    private long cnp;
    private String nume;
    private int[] imobile;

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
        if (imobile == null) {
            imobile = new int[1];
            imobile[0] = imobil;
        } else {
            int[] imobile = new int[this.imobile.length + 1];
            System.arraycopy(this.imobile, 0, imobile, 0, this.imobile.length);
            imobile[imobile.length - 1] = imobil;
            this.imobile = imobile;
        }
    }

    @Override
    public String toString() {
        return "{" + cnp +
                "," + nume +
                "," + Arrays.toString(imobile) +
                '}';
    }

    @Override
    public int inregistrare(int idImobil) {
        this.addImobil(idImobil);
        return imobile.length;
    }
}
