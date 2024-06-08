public class Departament {
    public int codDepartament;
    public String numire;
    public Double buget;
    public String manager;

    public int getCodDepartament() {
        return codDepartament;
    }

    public void setCodDepartament(int codDepartament) {
        this.codDepartament = codDepartament;
    }

    public String getNumire() {
        return numire;
    }

    public void setNumire(String numire) {
        this.numire = numire;
    }

    public Double getBuget() {
        return buget;
    }

    public void setBuget(Double buget) {
        this.buget = buget;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Departament(int codDepartament, String numire, Double buget, String manager) {
        this.codDepartament = codDepartament;
        this.numire = numire;
        this.buget = buget;
        this.manager = manager;
    }

    public Departament() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Departament{");
        sb.append("codDepartament=").append(codDepartament);
        sb.append(", numire='").append(numire).append('\'');
        sb.append(", buget=").append(buget);
        sb.append(", manager='").append(manager).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
