public class Pacient {
    public String cnp;
    public String nume;
    public int varsta;
    public int sectie;

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

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getSectie() {
        return sectie;
    }

    public void setSectie(int sectie) {
        this.sectie = sectie;
    }

    public Pacient(String cnp, String nume, int varsta, int sectie) {
        this.cnp = cnp;
        this.nume = nume;
        this.varsta = varsta;
        this.sectie = sectie;
    }

    public Pacient() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pacient{");
        sb.append("cnp='").append(cnp).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", varsta=").append(varsta);
        sb.append(", sectie=").append(sectie);
        sb.append('}');
        return sb.toString();
    }
}
