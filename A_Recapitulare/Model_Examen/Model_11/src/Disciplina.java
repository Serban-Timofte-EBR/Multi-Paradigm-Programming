enum TIP {
    optionala,
    obligatorie
}
public class Disciplina {
    public int codDisciplina;
    public String denumire;
    public TIP tip;

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public TIP getTip() {
        return tip;
    }

    public void setTip(TIP tip) {
        this.tip = tip;
    }

    public Disciplina(int codDisciplina, String denumire, TIP tip) {
        this.codDisciplina = codDisciplina;
        this.denumire = denumire;
        this.tip = tip;
    }

    public Disciplina() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Disciplina{");
        sb.append("codDisciplina=").append(codDisciplina);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tip=").append(tip);
        sb.append('}');
        return sb.toString();
    }
}
