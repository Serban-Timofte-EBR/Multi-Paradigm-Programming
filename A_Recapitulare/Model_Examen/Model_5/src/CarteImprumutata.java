public class CarteImprumutata {
    private String titlu;
    private String data;

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CarteImprumutata(String titlu, String data) {
        this.titlu = titlu;
        this.data = data;
    }

    public CarteImprumutata() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarteImprumutata{");
        sb.append("titlu='").append(titlu).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
