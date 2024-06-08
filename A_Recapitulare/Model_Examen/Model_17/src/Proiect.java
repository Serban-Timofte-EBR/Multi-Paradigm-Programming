public class Proiect {
    public int cod_proiect;
    public String nume_proiect;
    public String manager;
    public Double buget;

    public int getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(int cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getNume_proiect() {
        return nume_proiect;
    }

    public void setNume_proiect(String nume_proiect) {
        this.nume_proiect = nume_proiect;
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

    public Proiect(int cod_proiect, String nume_proiect, String manager, Double buget) {
        this.cod_proiect = cod_proiect;
        this.nume_proiect = nume_proiect;
        this.manager = manager;
        this.buget = buget;
    }

    public Proiect() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod_proiect=").append(cod_proiect);
        sb.append(", nume_proiect='").append(nume_proiect).append('\'');
        sb.append(", manager='").append(manager).append('\'');
        sb.append(", buget=").append(buget);
        sb.append('}');
        return sb.toString();
    }
}
