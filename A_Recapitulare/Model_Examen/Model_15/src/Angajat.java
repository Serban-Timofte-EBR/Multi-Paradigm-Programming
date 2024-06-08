public class Angajat {
    public int codAngajat;
    public int codCompanie;
    public String nume;
    public Double salariul;

    public int getCodAngajat() {
        return codAngajat;
    }

    public void setCodAngajat(int codAngajat) {
        this.codAngajat = codAngajat;
    }

    public int getCodCompanie() {
        return codCompanie;
    }

    public void setCodCompanie(int codCompanie) {
        this.codCompanie = codCompanie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Double getSalariul() {
        return salariul;
    }

    public void setSalariul(Double salariul) {
        this.salariul = salariul;
    }

    public Angajat(int codAngajat, int codCompanie, String nume, Double salariul) {
        this.codAngajat = codAngajat;
        this.codCompanie = codCompanie;
        this.nume = nume;
        this.salariul = salariul;
    }

    public Angajat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Angajati{");
        sb.append("codAngajat=").append(codAngajat);
        sb.append(", codCompanie=").append(codCompanie);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", salariul=").append(salariul);
        sb.append('}');
        return sb.toString();
    }
}
