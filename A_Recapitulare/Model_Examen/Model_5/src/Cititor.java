import java.util.List;

public class Cititor {
    private String nume;
    private List<CarteImprumutata> cartiImprumutate;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public List<CarteImprumutata> getCartiImprumutate() {
        return cartiImprumutate;
    }

    public void setCartiImprumutate(List<CarteImprumutata> cartiImprumutate) {
        this.cartiImprumutate = cartiImprumutate;
    }

    public Cititor(String nume, List<CarteImprumutata> cartiImprumutate) {
        this.nume = nume;
        this.cartiImprumutate = cartiImprumutate;
    }

    public Cititor() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cititor{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", cartiImprumutate=").append(cartiImprumutate);
        sb.append('}');
        return sb.toString();
    }
}
