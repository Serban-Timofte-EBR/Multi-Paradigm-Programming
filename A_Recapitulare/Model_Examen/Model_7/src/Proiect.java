import java.util.List;

public class Proiect {
    private int cod_proiect;
    private String nume_proiect;
    private Double buget;
    private List<Integer> echipa;

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

    public Double getBuget() {
        return buget;
    }

    public void setBuget(Double buget) {
        this.buget = buget;
    }

    public List<Integer> getEchipa() {
        return echipa;
    }

    public void setEchipa(List<Integer> echipa) {
        this.echipa = echipa;
    }

    public Proiect(int cod_proiect, String nume_proiect, Double buget, List<Integer> echipa) {
        this.cod_proiect = cod_proiect;
        this.nume_proiect = nume_proiect;
        this.buget = buget;
        this.echipa = echipa;
    }

    public Proiect() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod_proiect=").append(cod_proiect);
        sb.append(", nume_proiect='").append(nume_proiect).append('\'');
        sb.append(", buget=").append(buget);
        sb.append(", echipa=").append(echipa);
        sb.append('}');
        return sb.toString();
    }
}
