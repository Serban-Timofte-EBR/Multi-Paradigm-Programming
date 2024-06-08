public class Capitol {
    public int codCapitol;
    public int codSantier;
    public String denumireCheltuiala;
    public String unitateMasura;
    public Double cantitate;
    public Double pretUnitar;

    public int getCodCapitol() {
        return codCapitol;
    }

    public void setCodCapitol(int codCapitol) {
        this.codCapitol = codCapitol;
    }

    public int getCodSantier() {
        return codSantier;
    }

    public void setCodSantier(int codSantier) {
        this.codSantier = codSantier;
    }

    public String getDenumireCheltuiala() {
        return denumireCheltuiala;
    }

    public void setDenumireCheltuiala(String denumireCheltuiala) {
        this.denumireCheltuiala = denumireCheltuiala;
    }

    public String getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(String unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public Double getCantitate() {
        return cantitate;
    }

    public void setCantitate(Double cantitate) {
        this.cantitate = cantitate;
    }

    public Double getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(Double pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public Capitol(int codCapitol, int codSantier, String denumireCheltuiala, String unitateMasura, Double cantitate, Double pretUnitar) {
        this.codCapitol = codCapitol;
        this.codSantier = codSantier;
        this.denumireCheltuiala = denumireCheltuiala;
        this.unitateMasura = unitateMasura;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
    }

    public Capitol() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Capitol{");
        sb.append("codCapitol=").append(codCapitol);
        sb.append(", codSantier=").append(codSantier);
        sb.append(", denumireCheltuiala='").append(denumireCheltuiala).append('\'');
        sb.append(", unitateMasura='").append(unitateMasura).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pretUnitar=").append(pretUnitar);
        sb.append('}');
        return sb.toString();
    }
}
