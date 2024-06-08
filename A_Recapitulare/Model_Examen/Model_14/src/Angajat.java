public class Angajat {
    public int codAngajat;
    public String nume;
    public int departament;
    public Double salariul;

    public int getCodAngajat() {
        return codAngajat;
    }

    public void setCodAngajat(int codAngajat) {
        this.codAngajat = codAngajat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getDepartament() {
        return departament;
    }

    public void setDepartament(int departament) {
        this.departament = departament;
    }

    public Double getSalariul() {
        return salariul;
    }

    public void setSalariul(Double salariul) {
        this.salariul = salariul;
    }

    public Angajat(int codAngajat, String nume, int departament, Double salariul) {
        this.codAngajat = codAngajat;
        this.nume = nume;
        this.departament = departament;
        this.salariul = salariul;
    }

    public Angajat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Angajat{");
        sb.append("codAngajat=").append(codAngajat);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", departament=").append(departament);
        sb.append(", salariul=").append(salariul);
        sb.append('}');
        return sb.toString();
    }
}
