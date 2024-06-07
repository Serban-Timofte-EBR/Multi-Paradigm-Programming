public class Cititor {
    public String nume;
    public String cotaCarte;
    public int numarZile;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCotaCarte() {
        return cotaCarte;
    }

    public void setCotaCarte(String cotaCarte) {
        this.cotaCarte = cotaCarte;
    }

    public int getNumarZile() {
        return numarZile;
    }

    public void setNumarZile(int numarZile) {
        this.numarZile = numarZile;
    }

    public Cititor(String nume, String cotaCarte, int numarZile) {
        this.nume = nume;
        this.cotaCarte = cotaCarte;
        this.numarZile = numarZile;
    }

    public Cititor() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cititor{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", cotaCarte='").append(cotaCarte).append('\'');
        sb.append(", numarZile=").append(numarZile);
        sb.append('}');
        return sb.toString();
    }
}
