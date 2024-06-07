public class Curs {
    public int codDisciplina;
    public String zi;
    public String interval;
    public String sala;
    public String formatie;

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getFormatie() {
        return formatie;
    }

    public void setFormatie(String formatie) {
        this.formatie = formatie;
    }

    public Curs(int codDisciplina, String zi, String interval, String sala, String formatie) {
        this.codDisciplina = codDisciplina;
        this.zi = zi;
        this.interval = interval;
        this.sala = sala;
        this.formatie = formatie;
    }

    public Curs() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Curs{");
        sb.append("codDisciplina=").append(codDisciplina);
        sb.append(", zi='").append(zi).append('\'');
        sb.append(", interval='").append(interval).append('\'');
        sb.append(", sala='").append(sala).append('\'');
        sb.append(", formatie='").append(formatie).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
