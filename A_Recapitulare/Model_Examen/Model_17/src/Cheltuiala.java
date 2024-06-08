public class Cheltuiala {
    public int cod_cheltuiala;
    public int cod_proiect;
    public String descriere;
    public String unitate_masura;
    public Double cantitate;
    public Double pret_unitar;

    public int getCod_cheltuiala() {
        return cod_cheltuiala;
    }

    public void setCod_cheltuiala(int cod_cheltuiala) {
        this.cod_cheltuiala = cod_cheltuiala;
    }

    public int getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(int cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getUnitate_masura() {
        return unitate_masura;
    }

    public void setUnitate_masura(String unitate_masura) {
        this.unitate_masura = unitate_masura;
    }

    public Double getCantitate() {
        return cantitate;
    }

    public void setCantitate(Double cantitate) {
        this.cantitate = cantitate;
    }

    public Double getPret_unitar() {
        return pret_unitar;
    }

    public void setPret_unitar(Double pret_unitar) {
        this.pret_unitar = pret_unitar;
    }

    public Cheltuiala(int cod_cheltuiala, int cod_proiect, String descriere, String unitate_masura, Double cantitate, Double pret_unitar) {
        this.cod_cheltuiala = cod_cheltuiala;
        this.cod_proiect = cod_proiect;
        this.descriere = descriere;
        this.unitate_masura = unitate_masura;
        this.cantitate = cantitate;
        this.pret_unitar = pret_unitar;
    }

    public Cheltuiala() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cheltuiala{");
        sb.append("cod_cheltuiala=").append(cod_cheltuiala);
        sb.append(", cod_proiect=").append(cod_proiect);
        sb.append(", descriere='").append(descriere).append('\'');
        sb.append(", unitate_masura='").append(unitate_masura).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pret_unitar=").append(pret_unitar);
        sb.append('}');
        return sb.toString();
    }
}
