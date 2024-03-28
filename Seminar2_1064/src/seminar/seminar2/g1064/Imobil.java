package seminar.seminar2.g1064;

import java.io.Serializable;
import java.util.Date;

public abstract class Imobil implements Comparable<Imobil>, Serializable {
    protected int id,suprafataUtila,etaje,nrCamere;
    protected String telefonP;
    protected Zona zona;
    protected double pret;
    protected Date dataP;

    public Imobil() {
    }

    public Imobil(int id) {
        this.id = id;
    }

    public Imobil(int id, int suprafataUtila, int etaje, int nrCamere,
                  String telefonP, Zona zona, double pret, Date dataP) {
        this.id = id;
        this.suprafataUtila = suprafataUtila;
        this.etaje = etaje;
        this.nrCamere = nrCamere;
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

    public int getNrCamere() {
        return nrCamere;
    }

    public void setNrCamere(int nrCamere) {
        this.nrCamere = nrCamere;
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

    @Override
    public int compareTo(Imobil imobil) {
        if (pret==imobil.pret){
            return 0;
        } else {
            return pret< imobil.pret?-1:1;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", suprafataUtila=" + suprafataUtila +
                ", etaje=" + etaje +
                ", nrCamere=" + nrCamere +
                ", telefonP='" + telefonP + '\'' +
                ", zona=" + zona +
                ", pret=" + pret +
                ", dataP=" + (dataP==null?"":Main.fmt.format(dataP)) +
                '}';
    }
}
