class Proiect {
    private long cod;
    private double buget;

    public Proiect(long cod, double buget) {
        this.cod = cod;
        this.buget = buget;
    }

    public long getCod() {
        return cod;
    }

    public double getBuget() {
        return buget;
    }

    public void adaugaLaBuget(double suma) {
        this.buget += suma;
    }

    @Override
    public String toString() {
        return "Proiect{" +
                "cod=" + cod +
                ", buget=" + buget +
                '}';
    }
}