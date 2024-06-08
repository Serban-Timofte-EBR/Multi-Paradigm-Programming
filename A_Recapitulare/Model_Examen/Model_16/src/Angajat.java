public class Angajat {
    public int codAngajat;
    public int codDepartament;
    public String nume;
    public Double salariul;

    public int getCodAngajat() {
        return codAngajat;
    }

    public void setCodAngajat(int codAngajat) {
        this.codAngajat = codAngajat;
    }

    public int getCodDepartament() {
        return codDepartament;
    }

    public void setCodDepartament(int codDepartament) {
        this.codDepartament = codDepartament;
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

    public Angajat(int codAngajat, int codDepartament, String nume, Double salariul) {
        this.codAngajat = codAngajat;
        this.codDepartament = codDepartament;
        this.nume = nume;
        this.salariul = salariul;
    }

    public Angajat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Angajat{");
        sb.append("codAngajat=").append(codAngajat);
        sb.append(", codDepartament=").append(codDepartament);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", salariul=").append(salariul);
        sb.append('}');
        return sb.toString();
    }
}
