public class Cost {
    public int codCost;
    public int codProiect;
    public String denumireCost;
    public String unitate;
    public Double cantitate;
    public Double pretUnitate;

    public int getCodCost() {
        return codCost;
    }

    public void setCodCost(int codCost) {
        this.codCost = codCost;
    }

    public int getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(int codProiect) {
        this.codProiect = codProiect;
    }

    public String getDenumireCost() {
        return denumireCost;
    }

    public void setDenumireCost(String denumireCost) {
        this.denumireCost = denumireCost;
    }

    public String getUnitate() {
        return unitate;
    }

    public void setUnitate(String unitate) {
        this.unitate = unitate;
    }

    public Double getCantitate() {
        return cantitate;
    }

    public void setCantitate(Double cantitate) {
        this.cantitate = cantitate;
    }

    public Double getPretUnitate() {
        return pretUnitate;
    }

    public void setPretUnitate(Double pretUnitate) {
        this.pretUnitate = pretUnitate;
    }

    public Cost(int codCost, int codProiect, String denumireCost, String unitate, Double cantitate, Double pretUnitate) {
        this.codCost = codCost;
        this.codProiect = codProiect;
        this.denumireCost = denumireCost;
        this.unitate = unitate;
        this.cantitate = cantitate;
        this.pretUnitate = pretUnitate;
    }

    public Cost() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cost{");
        sb.append("codCost=").append(codCost);
        sb.append(", codProiect=").append(codProiect);
        sb.append(", denumireCost='").append(denumireCost).append('\'');
        sb.append(", unitate='").append(unitate).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pretUnitate=").append(pretUnitate);
        sb.append('}');
        return sb.toString();
    }
}
