public class ContContabil {
    private String simbol;
    private String denumire;
    private char tip;
    private double soldInitial;
    private double rulajDebitor;
    private double rulajCreditor;

    public ContContabil(String simbol, String denumire, char tip, double soldInitial, double rulajDebitor, double rulajCreditor) {
        this.simbol = simbol;
        this.denumire = denumire;
        this.tip = tip;
        this.soldInitial = soldInitial;
        this.rulajDebitor = rulajDebitor;
        this.rulajCreditor = rulajCreditor;
    }

    public ContContabil() {
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public char getTip() {
        return tip;
    }

    public void setTip(char tip) {
        this.tip = tip;
    }

    public double getSoldInitial() {
        return soldInitial;
    }

    public void setSoldInitial(double soldInitial) {
        this.soldInitial = soldInitial;
    }

    public double getRulajDebitor() {
        return rulajDebitor;
    }

    public void setRulajDebitor(double rulajDebitor) {
        this.rulajDebitor = rulajDebitor;
    }

    public double getRulajCreditor() {
        return rulajCreditor;
    }

    public void setRulajCreditor(double rulajCreditor) {
        this.rulajCreditor = rulajCreditor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContContabil{");
        sb.append("simbol='").append(simbol).append('\'');
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tip=").append(tip);
        sb.append(", soldInitial=").append(soldInitial);
        sb.append(", rulajDebitor=").append(rulajDebitor);
        sb.append(", rulajCreditor=").append(rulajCreditor);
        sb.append('}');
        return sb.toString();
    }
}
