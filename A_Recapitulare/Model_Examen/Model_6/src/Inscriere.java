public class Inscriere {
    private String cnp;
    private String nume;
    private double notaBac;
    private int codSpecializare;

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getNotaBac() {
        return notaBac;
    }

    public void setNotaBac(double notaBac) {
        this.notaBac = notaBac;
    }

    public int getCodSpecializare() {
        return codSpecializare;
    }

    public void setCodSpecializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }

    public Inscriere(String cnp, String nume, double notaBac, int codSpecializare) {
        this.cnp = cnp;
        this.nume = nume;
        this.notaBac = notaBac;
        this.codSpecializare = codSpecializare;
    }

    public Inscriere() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Inscriere{");
        sb.append("cnp='").append(cnp).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", notaBac=").append(notaBac);
        sb.append(", codSpecializare=").append(codSpecializare);
        sb.append('}');
        return sb.toString();
    }
}
