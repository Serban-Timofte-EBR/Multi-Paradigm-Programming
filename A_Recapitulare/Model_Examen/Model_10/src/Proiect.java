public class Proiect {
    public int cod;
    public String nume;
    public String descriere;
    public String manager;
    public Double buget;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
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

    public Proiect(int cod, String nume, String descriere, String manager, Double buget) {
        this.cod = cod;
        this.nume = nume;
        this.descriere = descriere;
        this.manager = manager;
        this.buget = buget;
    }

    public Proiect() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod=").append(cod);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", descriere='").append(descriere).append('\'');
        sb.append(", manager='").append(manager).append('\'');
        sb.append(", buget=").append(buget);
        sb.append('}');
        return sb.toString();
    }
}
