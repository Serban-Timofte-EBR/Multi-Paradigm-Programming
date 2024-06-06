enum Tip {
    suprafata,
    persoane,
    apartament
}
public class Factura {
    private String denumire;
    private Tip repartizare;
    private double valoare;

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Tip getRepartizare() {
        return repartizare;
    }

    public void setRepartizare(Tip repartizare) {
        this.repartizare = repartizare;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public Factura(String denumire, Tip repartizare, double valoare) {
        this.denumire = denumire;
        this.repartizare = repartizare;
        this.valoare = valoare;
    }

    public Factura() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Factura{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", repartizare=").append(repartizare);
        sb.append(", valoare=").append(valoare);
        sb.append('}');
        return sb.toString();
    }
}
