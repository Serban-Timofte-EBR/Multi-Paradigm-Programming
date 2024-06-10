public class Rezervare {
    public int codRezervare;
    public int codAventura;
    public int numarLocuriRezevate;

    public int getCodRezervare() {
        return codRezervare;
    }

    public void setCodRezervare(int codRezervare) {
        this.codRezervare = codRezervare;
    }

    public int getCodAventura() {
        return codAventura;
    }

    public void setCodAventura(int codAventura) {
        this.codAventura = codAventura;
    }

    public int getNumarLocuriRezevate() {
        return numarLocuriRezevate;
    }

    public void setNumarLocuriRezevate(int numarLocuriRezevate) {
        this.numarLocuriRezevate = numarLocuriRezevate;
    }

    public Rezervare(int codRezervare, int codAventura, int numarLocuriRezevate) {
        this.codRezervare = codRezervare;
        this.codAventura = codAventura;
        this.numarLocuriRezevate = numarLocuriRezevate;
    }

    public Rezervare() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rezervare{");
        sb.append("codRezervare=").append(codRezervare);
        sb.append(", codAventura=").append(codAventura);
        sb.append(", numarLocuriRezevate=").append(numarLocuriRezevate);
        sb.append('}');
        return sb.toString();
    }
}
