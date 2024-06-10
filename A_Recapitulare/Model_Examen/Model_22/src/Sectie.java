public class Sectie {
    public int codSectie;
    public String denumire;
    public int numarLocuri;

    public int getCodSectie() {
        return codSectie;
    }

    public void setCodSectie(int codSectie) {
        this.codSectie = codSectie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }

    public Sectie(int codSectie, String denumire, int numarLocuri) {
        this.codSectie = codSectie;
        this.denumire = denumire;
        this.numarLocuri = numarLocuri;
    }

    public Sectie() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sectie{");
        sb.append("codSectie=").append(codSectie);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", numarLocuri=").append(numarLocuri);
        sb.append('}');
        return sb.toString();
    }
}
