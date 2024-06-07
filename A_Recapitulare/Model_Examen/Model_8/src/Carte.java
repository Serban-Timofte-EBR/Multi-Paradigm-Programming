public class Carte {
    public String cotaCarte;
    public String titlu;
    public String autor;
    public int anPublicare;

    public Carte(String cotaCarte, String titlu, String autor, int anPublicare) {
        this.cotaCarte = cotaCarte;
        this.titlu = titlu;
        this.autor = autor;
        this.anPublicare = anPublicare;
    }

    public Carte() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("cotaCarte='").append(cotaCarte).append('\'');
        sb.append(", titlu='").append(titlu).append('\'');
        sb.append(", autor='").append(autor).append('\'');
        sb.append(", anPublicare=").append(anPublicare);
        sb.append('}');
        return sb.toString();
    }
}
