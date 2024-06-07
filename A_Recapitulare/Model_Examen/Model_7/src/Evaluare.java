public class Evaluare {
    private int id_angajat;
    private int an;
    private double scor;

    public int getId_angajat() {
        return id_angajat;
    }

    public void setId_angajat(int id_angajat) {
        this.id_angajat = id_angajat;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public double getScor() {
        return scor;
    }

    public void setScor(double scor) {
        this.scor = scor;
    }

    public Evaluare(int id_angajat, int an, double scor) {
        this.id_angajat = id_angajat;
        this.an = an;
        this.scor = scor;
    }

    public Evaluare() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Evaluare{");
        sb.append("id_angajat=").append(id_angajat);
        sb.append(", an=").append(an);
        sb.append(", scor=").append(scor);
        sb.append('}');
        return sb.toString();
    }
}
