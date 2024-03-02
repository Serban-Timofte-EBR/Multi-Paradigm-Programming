package ro.ase.acs.classes;

public class Car {
    private String producer;    //accesat doar de clasa mea, in interiorul clasei
                                //protected -> accesat de clasa, clasele derivate de oriunde ar fi, fie de clasele din acelasi pachet
                                //package -> IMPLICITA, toate clasele din acelasi pachet pot accesa stributul
    private float price;
    //public enum FuelType {diesel, electric, gas};   //accesibil prin Car.FuelType
    private FuelType fueltype;
    private int[] distance;

    public Car() {              //membrii/metodele publice pot fi referite de oriunde (inclusiv alt proiect)
        producer = "";
        price = 4000;
        fueltype = FuelType.gas;
        distance = new int[1];
        distance[0] = 100;
    }

    public Car(String producer, float price) {
        this.producer = producer;
        this.price = price;
        fueltype = FuelType.gas;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public FuelType getFueltype() {
        return fueltype;
    }

    public void setFueltype(FuelType fueltype) {
        this.fueltype = fueltype;
    }

    public int[] getDistance() {    //sau acceasi metoda ca in setter
        if(this.distance != null) {
            int[] copy = new int[this.distance.length];
            System.arraycopy(distance, 0, copy, 0, distance.length);
            return copy;
        }
        return null;
    }

    public void setDistance(int[] distance) {
        if(distance != null) {
            this.distance = new int[distance.length];
            for (int i = 0; i < distance.length; i++) {
                this.distance[i] = distance[i];
            }
        }
    }

    @Override           //in java toate metodele sunt virtuale, deci toate se pot suprascrie        (exceptie fac cele statice)
    public Car clone() {
        Car copy = new Car();
        copy.price = price;
        copy.producer = producer;
        copy.fueltype = fueltype;
        copy.distance = getDistance();
        return copy;
    }
}