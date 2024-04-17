public class Punct implements  Comparable<Punct>{
    private String etichetaFigura;
    private String etichetaPunctului;
    private double oX;
    private double oY;

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

    public double getoX() {
        return oX;
    }

    public void setoX(double oX) {
        this.oX = oX;
    }

    public double getoY() {
        return oY;
    }

    public void setoY(double oY) {
        this.oY = oY;
    }

    public Punct() {
        etichetaFigura = "";
        etichetaPunctului = "";
        oX = 0;
        oY = 0;
    }

    public Punct(String etichetaFigura, String etichetaPunctului, double oX, double oY) {
        this.etichetaFigura = etichetaFigura;
        this.etichetaPunctului = etichetaPunctului;
        this.oX = oX;
        this.oY = oY;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Punct{");
        sb.append("etichetaFigura='").append(etichetaFigura).append('\'');
        sb.append(", etichetaPunctului='").append(etichetaPunctului).append('\'');
        sb.append(", oX=").append(oX);
        sb.append(", oY=").append(oY);
        sb.append('}');
        return sb.toString();
    }

    public double distanta() {
        return Math.sqrt(oX * oX + oY * oY);
    }


    @Override
    public int compareTo(Punct o) {
        return Double.compare(this.distanta(), o.distanta());
    }
}
