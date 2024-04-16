import java.util.Objects;

public class Carte implements Comparable<Carte> {
    private String autor;
    private String titlul;
    private String stil;
    private int nrPagini;

    public Carte() {
        autor = "";
        titlul = "";
        stil = "";
        nrPagini = 0;
    }

    public Carte(String autor, String titlul, String stil, int cost) {
        this.autor = autor;
        this.titlul = titlul;
        this.stil = stil;
        this.nrPagini = cost;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitlul() {
        return titlul;
    }

    public void setTitlul(String titlul) {
        this.titlul = titlul;
    }

    public String getStil() {
        return stil;
    }

    public void setStil(String stil) {
        this.stil = stil;
    }

    public int getNrPagini() {
        return nrPagini;
    }

    public void setNrPagini(int nrPagini) {
        this.nrPagini = nrPagini;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carte carte = (Carte) o;

        if (nrPagini != carte.nrPagini) return false;
        if (!Objects.equals(autor, carte.autor)) return false;
        if (!Objects.equals(titlul, carte.titlul)) return false;
        return Objects.equals(stil, carte.stil);
    }

    @Override
    public int hashCode() {
        int result = autor != null ? autor.hashCode() : 0;
        result = 31 * result + (titlul != null ? titlul.hashCode() : 0);
        result = 31 * result + (stil != null ? stil.hashCode() : 0);
        result = 31 * result + nrPagini;
        return result;
    }

    @Override
    public int compareTo(Carte o) {
        return Integer.compare(this.nrPagini, o.nrPagini);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("autor='").append(autor).append('\'');
        sb.append(", titlul='").append(titlul).append('\'');
        sb.append(", stil='").append(stil).append('\'');
        sb.append(", nrPagini=").append(nrPagini);
        sb.append('}');
        return sb.toString();
    }
}
