
public class Produs {
    private int id;
    private String denumire;
    private String categorie;
    private double pret;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Produs(int id, String denumire, String categorie, double pret) {
        this.id = id;
        this.denumire = denumire;
        this.categorie = categorie;
        this.pret = pret;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("id=").append(id);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", pret=").append(pret);
        sb.append('}');
        return sb.toString();
    }
}
