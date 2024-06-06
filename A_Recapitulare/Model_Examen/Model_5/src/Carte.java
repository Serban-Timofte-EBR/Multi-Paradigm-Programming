public class Carte {
    private String titlu;
    private String autor;
    private String gen;
    private int numarPagini;

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getNumarPagini() {
        return numarPagini;
    }

    public void setNumarPagini(int numarPagini) {
        this.numarPagini = numarPagini;
    }

    public Carte(String titlu, String autor, String gen, int numarPagini) {
        this.titlu = titlu;
        this.autor = autor;
        this.gen = gen;
        this.numarPagini = numarPagini;
    }

    public Carte() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("titlu='").append(titlu).append('\'');
        sb.append(", autor='").append(autor).append('\'');
        sb.append(", gen='").append(gen).append('\'');
        sb.append(", numarPagini=").append(numarPagini);
        sb.append('}');
        return sb.toString();
    }
}
