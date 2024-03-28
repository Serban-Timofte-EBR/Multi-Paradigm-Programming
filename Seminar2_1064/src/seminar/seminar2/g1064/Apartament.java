package seminar.seminar2.g1064;

import java.util.Arrays;
import java.util.Date;

public class Apartament extends Imobil implements Cloneable{
    private int etaj;
    private String[] dotari;

    public Apartament() {
    }

    public Apartament(int id) {
        super(id);
    }

    public Apartament(int id, int suprafataUtila, int etaje, int nrCamere, String telefonP, Zona zona, double pret,
                      Date dataP, int etaj, String[] dotari) throws Exception{
        super(id, suprafataUtila, etaje, nrCamere, telefonP, zona, pret, dataP);
        if (etaj>etaje){
            throw new Exception("Numar invalid de etaj!");
        }
        this.etaj = etaj;
        this.dotari = dotari;
    }

    public int getEtaj() {
        return etaj;
    }

    public void setEtaj(int etaj) throws Exception{
        if (etaj>etaje){
            throw new Exception("Numar invalid de etaj!");
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
    public Object clone() throws CloneNotSupportedException {
        Apartament clona = (Apartament) super.clone();
        clona.setDotari( Arrays.copyOf(this.dotari,this.dotari.length) );
        clona.setDataP((Date) dataP.clone());
        return clona;
    }

    @Override
    public String toString() {
        return "Apartament{" + super.toString()+
                "etaj=" + etaj +
                ", dotari=" + Arrays.toString(dotari) +
                "}";
    }

}
