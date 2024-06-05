public class MateriePrima {
    private int cod;
    private String denumire;
    private double cantitate;
    private double pretUnitar;
    private String unitateMasura;

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

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public double getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(double pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public String getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(String unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public MateriePrima(int cod, String denumire, double cantitate, double pretUnitar, String unitateMasura) {
        this.cod = cod;
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
        this.unitateMasura = unitateMasura;
    }

    public MateriePrima() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MateriePrima{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pretUnitar=").append(pretUnitar);
        sb.append(", unitateMasura='").append(unitateMasura).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
