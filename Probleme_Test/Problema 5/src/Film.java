public class Film implements Comparable<Film> {
    private String titlu;
    private int an;
    private String tip;
    private int durata;

    public Film(String titlu, int an, String tip, int durata) {
        this.titlu = titlu;
        this.an = an;
        this.tip = tip;
        this.durata = durata;
    }

    public Film() {
        titlu = "";
        an = 0;
        tip = "";
        durata = 0;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Film{");
        sb.append("titlu='").append(titlu).append('\'');
        sb.append(", an=").append(an);
        sb.append(", tip='").append(tip).append('\'');
        sb.append(", durata=").append(durata);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Film o) {
        return Integer.compare(this.durata, o.durata);
    }
}
