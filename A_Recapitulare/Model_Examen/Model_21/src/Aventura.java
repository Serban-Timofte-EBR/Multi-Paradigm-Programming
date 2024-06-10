public class Aventura {
    public int codAventura;
    public String denumire;
    public Double tarif;
    public int locuriDisponibile;

    public int getCodAventura() {
        return codAventura;
    }

    public void setCodAventura(int codAventura) {
        this.codAventura = codAventura;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    public Aventura(int codAventura, String denumire, Double tarif, int locuriDisponibile) {
        this.codAventura = codAventura;
        this.denumire = denumire;
        this.tarif = tarif;
        this.locuriDisponibile = locuriDisponibile;
    }

    public Aventura() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Aventura{");
        sb.append("codAventura=").append(codAventura);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tarif=").append(tarif);
        sb.append(", locuriDisponibile=").append(locuriDisponibile);
        sb.append('}');
        return sb.toString();
    }
}
