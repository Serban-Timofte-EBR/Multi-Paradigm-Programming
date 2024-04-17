import javax.xml.crypto.Data;

public class MeteoData {
    private String oras;
    private String data;
    private double temperatura;
    private int presiunea;

    public MeteoData() {
        oras = "";
        data = "";
        temperatura = 0.0;
        presiunea = 0;
    }

    public MeteoData(String oras, String data, double temperatura, int presiunea) {
        this.oras = oras;
        this.data = data;
        this.temperatura = temperatura;
        this.presiunea = presiunea;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getPresiunea() {
        return presiunea;
    }

    public void setPresiunea(int presiunea) {
        this.presiunea = presiunea;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteoData{");
        sb.append("oras='").append(oras).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append(", temperatura=").append(temperatura);
        sb.append(", presiunea=").append(presiunea);
        sb.append('}');
        return sb.toString();
    }
}