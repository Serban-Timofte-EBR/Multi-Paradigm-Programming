public class Proiect {
    public int codProiect;
    public String manager;
    public String localitate;
    public String strada;
    public String obiectiv;
    public Double valoare;

    public int getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(int codProiect) {
        this.codProiect = codProiect;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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

    public Proiect(int codProiect, String manager, String localitate, String strada, String obiectiv, Double valoare) {
        this.codProiect = codProiect;
        this.manager = manager;
        this.localitate = localitate;
        this.strada = strada;
        this.obiectiv = obiectiv;
        this.valoare = valoare;
    }

    public Proiect() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("codProiect=").append(codProiect);
        sb.append(", manager='").append(manager).append('\'');
        sb.append(", localitate='").append(localitate).append('\'');
        sb.append(", strada='").append(strada).append('\'');
        sb.append(", obiectiv='").append(obiectiv).append('\'');
        sb.append(", valoare=").append(valoare);
        sb.append('}');
        return sb.toString();
    }
}
