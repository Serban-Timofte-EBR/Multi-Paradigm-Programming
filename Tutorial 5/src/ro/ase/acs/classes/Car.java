package ro.ase.acs.classes;

import ro.ase.acs.interfaces.Taxable;

public final class Car extends Vehicle implements Taxable, Comparable<Car> { //nu se mai poate deriva din clasa Car
    private String color;
    private int capacity;

    public Car() {
        super();
        this.color = "White";
        this.capacity = 49;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Car(String name, int speed, String color, int capacity) {
        super(name, speed);
        this.color = color;
        this.capacity = capacity;
    }

    @Override
    public double computeTax() {
        float tax = 0;
        if (this.capacity < 2000) {
            tax = (float)capacity / 1000 * 50;
        }
        else {
            tax =  (float)capacity / 1000 * 100;
        }
        return tax < MIN_TAX ? MIN_TAX : tax;
    }

    @Override
    public final void move() {      //orice alta implements Taxable preia functia move() din Car
        System.out.println("The car is moving!");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Car copy = (Car) super.clone();
        copy.color = color;
        copy.capacity = capacity;
        return copy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("color='").append(color).append('\'');
        sb.append(", capacity=").append(capacity);
        sb.append(", name=").append(getName());
        sb.append(", speed=").append(getSpeed());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Car o) {
        if(capacity < o.capacity) {return -1;}
        else if (capacity == o.capacity) {return 0;}
        else {return 1;}
    }

    @Override
    public int hashCode() {
        return ((31 * getName().hashCode()) + getSpeed())
                * 31 * color.hashCode() + capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Car) {
            Car o = (Car) obj;
            return (getName().equals(o.getName()) &&
                        getSpeed() == o.getSpeed() &&
                            color.equals(o.color) &&
                                capacity == o.capacity );
        }
        else {
            return false;
        }
    }
}