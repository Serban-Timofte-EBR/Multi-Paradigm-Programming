import java.io.Serializable;
import java.util.Date;

//clasa abstracta
public abstract class Imobil implements Comparable<Imobil>, Serializable {    //generics functioneaza prin casturi ale obiectelor de tip Object la clasa specificata de  noi
    protected int id;
    protected int suprafataUtila;
    protected int etaje;
    protected int nrClasa;
    protected String telefonP;
    protected Zona zona;
    protected double pret;
    protected Date dataP;

    public Imobil() {
    }

    public Imobil(int id) {
        this.id = id;
    }

    public Imobil(int id, int suprafataUtila, int etaje, int nrClasa, String telefonP, Zona zona, double pret, Date dataP) {
        this.id = id;
        this.suprafataUtila = suprafataUtila;
        this.etaje = etaje;
        this.nrClasa = nrClasa;
        this.telefonP = telefonP;
        this.zona = zona;
        this.pret = pret;
        this.dataP = dataP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuprafataUtila() {
        return suprafataUtila;
    }

    public void setSuprafataUtila(int suprafataUtila) {
        this.suprafataUtila = suprafataUtila;
    }

    public int getEtaje() {
        return etaje;
    }

    public void setEtaje(int etaje) {
        this.etaje = etaje;
    }

    public int getNrClasa() {
        return nrClasa;
    }

    public void setNrClasa(int nrClasa) {
        this.nrClasa = nrClasa;
    }

    public String getTelefonP() {
        return telefonP;
    }

    public void setTelefonP(String telefonP) {
        this.telefonP = telefonP;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Date getDataP() {
        return dataP;
    }

    public void setDataP(Date dataP) {
        this.dataP = dataP;
    }

    //equals dupa id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Imobil imobil = (Imobil) o;

        return id == imobil.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    //comparam dupa pret
    @Override
    public int compareTo(Imobil o) {
        if(this.pret < o.pret) {return -1;}
        else if (this.pret == o.pret) {return  0;}
        return -1;
    }

    @Override
    public String toString() {
        return "\tImobil{" +
                "id=" + id +
                ", suprafataUtila=" + suprafataUtila +
                ", etaje=" + etaje +
                ", nrClasa=" + nrClasa +
                ", telefonP='" + telefonP + '\'' +
                ", zona=" + zona +
                ", pret=" + pret +
                ", dataP=" + (dataP == null ? "" : Main.fmt.format(dataP)) +
                '}';
    }
}
