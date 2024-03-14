import java.util.Arrays;
import java.util.Date;

public class Apartament extends Imobil implements Cloneable {
    private int etaj;
    private String[] dotari;

    public Apartament() {
    }

    public Apartament(int id) {
        super(id);
    }

    public Apartament(int id, int suprafataUtila, int etaje, int nrClasa, String telefonP,
                      Zona zona, double pret, Date dataP, int etaj, String[] dotari) throws Exception{
        super(id, suprafataUtila, etaje, nrClasa, telefonP, zona, pret, dataP);
        if(etaj > etaje) {
            throw new Exception("Invalid Number of floors!");
        }
        this.etaj = etaj;
        this.dotari = dotari;
    }

    public int getEtaj() {
        return etaj;
    }

    public void setEtaj(int etaj) throws Exception {
        if(etaj > etaje) {  //apartamentul se afla la un etaj mai mare decat are imobilul
            throw new Exception("Invalid Number of floors!");
        }
        this.etaj = etaj;
    }

    public String[] getDotari() {
        return dotari;
    }

    public void setDotari(String[] dotari) {
        this.dotari = dotari;
    }

    @Override
    public String toString() {
        return "\tApartament{" + super.toString() +
                "etaj=" + etaj +
                ", dotari=" + Arrays.toString(dotari) +
                "} ";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Apartament ap_clona = (Apartament) super.clone();
        ap_clona.setDotari(Arrays.copyOf(this.dotari, this.dotari.length));
        ap_clona.setDataP((Date) dataP.clone());
        return ap_clona;
    }
}
