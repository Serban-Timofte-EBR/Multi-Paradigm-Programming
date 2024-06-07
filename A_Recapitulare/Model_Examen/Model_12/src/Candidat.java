import java.util.Map;

public class Candidat {
    public String nume;
    public Map<Integer, Double> optiuni;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Map<Integer, Double> getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(Map<Integer, Double> optiuni) {
        this.optiuni = optiuni;
    }

    public Candidat(String nume, Map<Integer, Double> optiuni) {
        this.nume = nume;
        this.optiuni = optiuni;
    }

    public Candidat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Candidat{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", optiuni=").append(optiuni);
        sb.append('}');
        return sb.toString();
    }
}
