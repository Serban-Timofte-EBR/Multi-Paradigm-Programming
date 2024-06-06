public class Persoana {
    private int cod;
    private String cnp;
    private String nume;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Persoana(int cod, String cnp, String nume) {
        this.cod = cod;
        this.cnp = cnp;
        this.nume = nume;
    }

    public Persoana() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Persoana{");
        sb.append("cod=").append(cod);
        sb.append(", cnp='").append(cnp).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
