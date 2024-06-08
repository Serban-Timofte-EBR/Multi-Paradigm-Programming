public class Departament {
    public int codDepartament;
    public String denumire;
    public String manager;
    public Double buget;

    public int getCodDepartament() {
        return codDepartament;
    }

    public void setCodDepartament(int codDepartament) {
        this.codDepartament = codDepartament;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getBuget() {
        return buget;
    }

    public void setBuget(Double buget) {
        this.buget = buget;
    }

    public Departament(int codDepartament, String denumire, String manager, Double buget) {
        this.codDepartament = codDepartament;
        this.denumire = denumire;
        this.manager = manager;
        this.buget = buget;
    }

    public Departament() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Departament{");
        sb.append("codDepartament=").append(codDepartament);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", manager='").append(manager).append('\'');
        sb.append(", buget=").append(buget);
        sb.append('}');
        return sb.toString();
    }
}
