public class Santier {
//    cod santier (intreg), localitate (sir), strada (sir), obiectiv (sir),
//    valoare (valoare estimata prin autorizatia de constructie - real).
    public int codSantier;
    public String localitate;
    public String strada;
    public String obiectiv;
    public Double valoare;

    public int getCodSantier() {
        return codSantier;
    }

    public void setCodSantier(int codSantier) {
        this.codSantier = codSantier;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getObiectiv() {
        return obiectiv;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public Santier(int codSantier, String localitate, String strada, String obiectiv, Double valoare) {
        this.codSantier = codSantier;
        this.localitate = localitate;
        this.strada = strada;
        this.obiectiv = obiectiv;
        this.valoare = valoare;
    }

    public Santier() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Santier{");
        sb.append("codSantier=").append(codSantier);
        sb.append(", localitate='").append(localitate).append('\'');
        sb.append(", strada='").append(strada).append('\'');
        sb.append(", obiectiv='").append(obiectiv).append('\'');
        sb.append(", valoare=").append(valoare);
        sb.append('}');
        return sb.toString();
    }
}
