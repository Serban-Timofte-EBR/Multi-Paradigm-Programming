public class Specializare {
    private int cod;
    private String denumire;
    private int locuri;

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

    public int getLocuri() {
        return locuri;
    }

    public void setLocuri(int locuri) {
        this.locuri = locuri;
    }

    public Specializare(int cod, String denumire, int locuri) {
        this.cod = cod;
        this.denumire = denumire;
        this.locuri = locuri;
    }

    public Specializare() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Specializare{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", locuri=").append(locuri);
        sb.append('}');
        return sb.toString();
    }
}
