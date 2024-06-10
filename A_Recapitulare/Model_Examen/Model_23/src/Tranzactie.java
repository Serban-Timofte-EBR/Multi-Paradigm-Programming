enum TIP {
    intrare, iesire
}
public class Tranzactie {
    public int codProdus;
    public int cantitate;
    public TIP tip;

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public TIP getTip() {
        return tip;
    }

    public void setTip(TIP tip) {
        this.tip = tip;
    }

    public Tranzactie(int codProdus, int cantitate, TIP tip) {
        this.codProdus = codProdus;
        this.cantitate = cantitate;
        this.tip = tip;
    }

    public Tranzactie() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("codProdus=").append(codProdus);
        sb.append(", cantitate=").append(cantitate);
        sb.append(", tip=").append(tip);
        sb.append('}');
        return sb.toString();
    }
}
