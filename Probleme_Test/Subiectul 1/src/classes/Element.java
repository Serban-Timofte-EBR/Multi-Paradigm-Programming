package classes;

import java.io.Serializable;

public class Element implements Comparable<Element>, Serializable {
    public int linie;
    public int coloana;
    public double val;

    public int getLinie() {
        return linie;
    }

    public void setLinie(int linie) {
        this.linie = linie;
    }

    public int getColoana() {
        return coloana;
    }

    public void setColoana(int coloana) {
        this.coloana = coloana;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public Element() {
        linie = -1;
        coloana = -1;
        val = -1.0;
    }

    public Element(int linie, int coloana, double val) {
        this.linie = linie;
        this.coloana = coloana;
        this.val = val;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Element{");
        sb.append("linie=").append(linie);
        sb.append(", coloana=").append(coloana);
        sb.append(", val=").append(val);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (linie != element.linie) return false;
        if (coloana != element.coloana) return false;
        return Double.compare(val, element.val) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = linie;
        result = 31 * result + coloana;
        temp = Double.doubleToLongBits(val);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Element o) {
        return Double.compare(val, o.val);
    }
}
