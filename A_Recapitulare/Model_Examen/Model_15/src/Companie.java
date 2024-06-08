public class Companie {
    public int codCompanie;
    public String denumire;
    public String localitate;
    public Double cifraDeAfaceri;

    public int getCodCompanie() {
        return codCompanie;
    }

    public void setCodCompanie(int codCompanie) {
        this.codCompanie = codCompanie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public Double getCifraDeAfaceri() {
        return cifraDeAfaceri;
    }

    public void setCifraDeAfaceri(Double cifraDeAfaceri) {
        this.cifraDeAfaceri = cifraDeAfaceri;
    }

    public Companie(int codCompanie, String denumire, String localitate, Double cifraDeAfaceri) {
        this.codCompanie = codCompanie;
        this.denumire = denumire;
        this.localitate = localitate;
        this.cifraDeAfaceri = cifraDeAfaceri;
    }

    public Companie() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Companii{");
        sb.append("codCompanie=").append(codCompanie);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", localitate='").append(localitate).append('\'');
        sb.append(", cifraDeAfaceri=").append(cifraDeAfaceri);
        sb.append('}');
        return sb.toString();
    }
}
