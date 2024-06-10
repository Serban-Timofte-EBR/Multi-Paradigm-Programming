import java.util.Map;

public class Candidat {
    public int codCandidat;
    public String numeCandidat;
    public double media;
    public Map<Integer, Integer> optiuni;

    public int getCodCandidat() {
        return codCandidat;
    }

    public void setCodCandidat(int codCandidat) {
        this.codCandidat = codCandidat;
    }

    public String getNumeCandidat() {
        return numeCandidat;
    }

    public void setNumeCandidat(String numeCandidat) {
        this.numeCandidat = numeCandidat;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public Map<Integer, Integer> getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(Map<Integer, Integer> optiuni) {
        this.optiuni = optiuni;
    }

    public Candidat(int codCandidat, String numeCandidat, double media, Map<Integer, Integer> optiuni) {
        this.codCandidat = codCandidat;
        this.numeCandidat = numeCandidat;
        this.media = media;
        this.optiuni = optiuni;
    }

    public Candidat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Candidat{");
        sb.append("codCandidat=").append(codCandidat);
        sb.append(", numeCandidat='").append(numeCandidat).append('\'');
        sb.append(", media=").append(media);
        sb.append(", optiuni=").append(optiuni);
        sb.append('}');
        return sb.toString();
    }
}
