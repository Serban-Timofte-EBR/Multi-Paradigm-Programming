import java.util.Arrays;
import java.util.Date;

public class Casa extends Imobil implements Cloneable{
    private int suprafataTeren;
    private String[] utilitati;

    public Casa() {
    }

    public Casa(int id) {
        super(id);
    }

    public Casa(int id, int suprafataUtila, int etaje, int nrClasa, String telefonP, Zona zona, double pret,
                Date dataP, int suprafataTeren, String[] utilitati) {
        super(id, suprafataUtila, etaje, nrClasa, telefonP, zona, pret, dataP);
        this.suprafataTeren = suprafataTeren;
        this.utilitati = utilitati;
    }

    public int getSuprafataTeren() {
        return suprafataTeren;
    }

    public void setSuprafataTeren(int suprafataTeren) {
        this.suprafataTeren = suprafataTeren;
    }

    public String[] getUtilitati() {
        return utilitati;
    }

    public void setUtilitati(String[] utilitati) {
        this.utilitati = utilitati;
    }

    @Override
    public String toString() {
        return "\tCasa{" + super.toString() +
                "suprafataTeren=" + suprafataTeren +
                ", utilitati=" + Arrays.toString(utilitati) +
                "} ";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Casa casa_clona = (Casa) super.clone();
        casa_clona.setUtilitati(Arrays.copyOf(this.utilitati, this.utilitati.length));
        casa_clona.dataP = ((Date) dataP.clone());
        return casa_clona;
    }
}
