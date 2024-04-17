import java.io.Serializable;
import java.util.Objects;

public class Carte implements Comparable<Carte>, Serializable {
    private String autor;
    private String titlu;
    private String gen;
    private int an;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public Carte() {
    }

    public Carte(String autor, String titlu, String gen, int an) {
        this.autor = autor;
        this.titlu = titlu;
        this.gen = gen;
        this.an = an;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("autor='").append(autor).append('\'');
        sb.append(", titlu='").append(titlu).append('\'');
        sb.append(", gen='").append(gen).append('\'');
        sb.append(", an=").append(an);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carte carte = (Carte) o;

        if (!Objects.equals(autor, carte.autor)) return false;
        if (!Objects.equals(titlu, carte.titlu)) return false;
        return Objects.equals(gen, carte.gen);
    }

    @Override
    public int hashCode() {
        int result = autor != null ? autor.hashCode() : 0;
        result = 31 * result + (titlu != null ? titlu.hashCode() : 0);
        result = 31 * result + (gen != null ? gen.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Carte o) {
        return Integer.compare(this.an, o.an);
    }
}
