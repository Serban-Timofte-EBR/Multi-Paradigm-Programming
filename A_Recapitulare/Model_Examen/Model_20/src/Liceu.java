import java.util.Map;

public class Liceu {
    public int codLiceu;
    public String numeLiceu;
    public int nrOptiuni;
    public Map<Integer, Integer> specialiazari;

    public int getCodLiceu() {
        return codLiceu;
    }

    public void setCodLiceu(int codLiceu) {
        this.codLiceu = codLiceu;
    }

    public String getNumeLiceu() {
        return numeLiceu;
    }

    public void setNumeLiceu(String numeLiceu) {
        this.numeLiceu = numeLiceu;
    }

    public int getNrOptiuni() {
        return nrOptiuni;
    }

    public void setNrOptiuni(int nrOptiuni) {
        this.nrOptiuni = nrOptiuni;
    }

    public Map<Integer, Integer> getSpecialiazari() {
        return specialiazari;
    }

    public void setSpecialiazari(Map<Integer, Integer> specialiazari) {
        this.specialiazari = specialiazari;
    }

    public int counterLocuri() {
        return specialiazari.entrySet().stream().mapToInt(value -> value.getValue()).sum();
    }

    public Liceu(int codLiceu, String numeLiceu, int nrOptiuni, Map<Integer, Integer> specialiazari) {
        this.codLiceu = codLiceu;
        this.numeLiceu = numeLiceu;
        this.nrOptiuni = nrOptiuni;
        this.specialiazari = specialiazari;
    }

    public Liceu() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Liceu{");
        sb.append("codLiceu=").append(codLiceu);
        sb.append(", numeLiceu='").append(numeLiceu).append('\'');
        sb.append(", nrOptiuni=").append(nrOptiuni);
        sb.append(", specialiazari=").append(specialiazari);
        sb.append('}');
        return sb.toString();
    }
}
