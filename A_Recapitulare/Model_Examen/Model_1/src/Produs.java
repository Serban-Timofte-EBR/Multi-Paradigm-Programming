import java.util.Map;

public class Produs {
    private int codProdus;
    private String denumireProdus;
    private Map<Integer, Double> consumuri;
    private int cantitate;
    private String unitateMasura;

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public Map<Integer, Double> getConsumuri() {
        return consumuri;
    }

    public void setConsumuri(Map<Integer, Double> consumuri) {
        this.consumuri = consumuri;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(String unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public Produs(int codProdus, String denumireProdus, Map<Integer, Double> consumuri, int cantitate, String unitateMasura) {
        this.codProdus = codProdus;
        this.denumireProdus = denumireProdus;
        this.consumuri = consumuri;
        this.cantitate = cantitate;
        this.unitateMasura = unitateMasura;
    }

    public Produs() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("codProdus=").append(codProdus);
        sb.append(", denumireProdus='").append(denumireProdus).append('\'');
        sb.append(", consumuri=").append(consumuri);
        sb.append(", cantitate=").append(cantitate);
        sb.append(", unitateMasura='").append(unitateMasura).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
