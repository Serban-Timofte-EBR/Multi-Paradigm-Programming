public class ProiectFinalizat {
    private int cod_proiect;
    private String nume_proiect;
    private int an_finalizare;

    public int getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(int cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getNume_proiect() {
        return nume_proiect;
    }

    public void setNume_proiect(String nume_proiect) {
        this.nume_proiect = nume_proiect;
    }

    public int getAn_finalizare() {
        return an_finalizare;
    }

    public void setAn_finalizare(int an_finalizare) {
        this.an_finalizare = an_finalizare;
    }

    public ProiectFinalizat(int cod_proiect, String nume_proiect, int an_finalizare) {
        this.cod_proiect = cod_proiect;
        this.nume_proiect = nume_proiect;
        this.an_finalizare = an_finalizare;
    }

    public ProiectFinalizat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProiectFinalizat{");
        sb.append("cod_proiect=").append(cod_proiect);
        sb.append(", nume_proiect='").append(nume_proiect).append('\'');
        sb.append(", an_finalizare=").append(an_finalizare);
        sb.append('}');
        return sb.toString();
    }
}
