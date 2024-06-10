public class Produs {
    public int cod;
    public String denumire;
    public double pret;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Produs(int cod, String denumire, double pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.pret = pret;
    }

    public Produs() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", pret=").append(pret);
        sb.append('}');
        return sb.toString();
    }
}
