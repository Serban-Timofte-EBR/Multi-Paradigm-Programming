public class Distante implements Comparable<Distante> {
    private String etichetaFigura;
    private String etichetaPunctului;

    private double distanta;

    public Distante() {
    }

    public Distante(String etichetaFigura, String etichetaPunctului, double distanta) {
        this.etichetaFigura = etichetaFigura;
        this.etichetaPunctului = etichetaPunctului;
        this.distanta = distanta;
    }

    public String getEtichetaFigura() {
        return etichetaFigura;
    }

    public void setEtichetaFigura(String etichetaFigura) {
        this.etichetaFigura = etichetaFigura;
    }

    public String getEtichetaPunctului() {
        return etichetaPunctului;
    }

    public void setEtichetaPunctului(String etichetaPunctului) {
        this.etichetaPunctului = etichetaPunctului;
    }

    public double getDistanta() {
        return distanta;
    }

    public void setDistanta(double distanta) {
        this.distanta = distanta;
    }

    @Override
    public int compareTo(Distante o) {
        return Double.compare(o.distanta, this.distanta);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Distante{");
        sb.append("etichetaFigura='").append(etichetaFigura).append('\'');
        sb.append(", etichetaPunctului='").append(etichetaPunctului).append('\'');
        sb.append(", distanta=").append(distanta);
        sb.append('}');
        return sb.toString();
    }
}
