import java.io.Serializable;

public class Proiect implements Serializable, Comparable<Proiect> {
    private long cod;
    private String acronim;
    private int anul;
    private String department;
    private double buget;
    private int durata;
    private double cheltuieli;
    private String director;

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    public String getAcronim() {
        return acronim;
    }

    public void setAcronim(String acronim) {
        this.acronim = acronim;
    }

    public int getAnul() {
        return anul;
    }

    public void setAnul(int anul) {
        this.anul = anul;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getBuget() {
        return buget;
    }

    public void setBuget(double buget) {
        this.buget = buget;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public double getCheltuieli() {
        return cheltuieli;
    }

    public void setCheltuieli(double cheltuieli) {
        this.cheltuieli = cheltuieli;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Proiect() {
    }

    public Proiect(long cod, String acronim, int anul, String department, double buget, int durata, String director){
        this.cod = cod;
        this.acronim = acronim;
        this.anul = anul;
        this.department = department;
        this.buget = buget;
        this.durata = durata;
        this.director = director;
        this.cheltuieli = 0.0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod=").append(cod);
        sb.append(", acronim='").append(acronim).append('\'');
        sb.append(", anul=").append(anul);
        sb.append(", department='").append(department).append('\'');
        sb.append(", buget=").append(buget);
        sb.append(", durata=").append(durata);
        sb.append(", director='").append(director).append('\'');
        sb.append(", cheltuieli=").append(cheltuieli);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int compareTo(Proiect o) {
        double bugetThis = this.buget - this.cheltuieli;
        double bugetO = o.buget - o.cheltuieli;

        return Double.compare(bugetThis, bugetO);
    }
}
