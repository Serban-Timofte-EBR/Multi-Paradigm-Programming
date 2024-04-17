import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Proiect implements Comparable<Proiect>, Serializable {
    private int cod;
    private String acronim;
    private String departament;
    private double buget;
    private int numarMembrii;

    public Proiect() {
        cod = 0;
        acronim = "";
        departament = "";
        buget = 0;
        numarMembrii = 0;
    }

    public Proiect(int cod, String acronim, String departament, double buget, int numarMembrii) {
        this.cod = cod;
        this.acronim = acronim;
        this.departament = departament;
        this.buget = buget;
        this.numarMembrii = numarMembrii;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getAcronim() {
        return acronim;
    }

    public void setAcronim(String acronim) {
        this.acronim = acronim;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public double getBuget() {
        return buget;
    }

    public void setBuget(double buget) {
        this.buget = buget;
    }

    public int getNumarMembrii() {
        return numarMembrii;
    }

    public void setNumarMembrii(int numarMembrii) {
        this.numarMembrii = numarMembrii;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod=").append(cod);
        sb.append(", acronim='").append(acronim).append('\'');
        sb.append(", departament='").append(departament).append('\'');
        sb.append(", buget=").append(buget);
        sb.append(", numarMembrii=").append(numarMembrii);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proiect proiect = (Proiect) o;

        return cod == proiect.cod;
    }

    @Override
    public int hashCode() {
        return cod;
    }

    @Override
    public int compareTo(Proiect o) {
        return Double.compare(this.buget, o.buget);
    }
}
