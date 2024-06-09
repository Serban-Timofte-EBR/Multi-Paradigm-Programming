public class Vanzare {
    public int id;
    public int idProdus;
    public int cantitate;
    public String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Vanzare(int id, int idProdus, int cantitate, String data) {
        this.id = id;
        this.idProdus = idProdus;
        this.cantitate = cantitate;
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vanzare{");
        sb.append("id=").append(id);
        sb.append(", idProdus=").append(idProdus);
        sb.append(", cantitate=").append(cantitate);
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
