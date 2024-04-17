class NotaContabila {
    private String simbol;
    private char tip; // 'D' pentru debitor sau 'C' pentru creditor
    private double suma;

    public NotaContabila(String simbol, char tip, double suma) {
        this.simbol = simbol;
        this.tip = tip;
        this.suma = suma;
    }

    public NotaContabila() {
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public char getTip() {
        return tip;
    }

    public void setTip(char tip) {
        this.tip = tip;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotaContabila{");
        sb.append('}');
        return sb.toString();
    }
}
