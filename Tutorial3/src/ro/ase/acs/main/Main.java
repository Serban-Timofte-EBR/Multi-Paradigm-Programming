package ro.ase.acs.main;

import ro.ase.acs.classes.Car;
import ro.ase.acs.classes.Vehicle;
import ro.ase.acs.interfaces.Taxable;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Taxable t;
        Vehicle v;  //nu implica si chemarea constructorului => v = null || nu se poate Vehicle v = new Vehicle()

        Car car = new Car("Dacia", 90, "black", 1400);
        System.out.println(car.getName());

        t = car; //upcasting
        double tax = t.computeTax(); //se apeleaza metoda din car ( pentru ca este facut upcasting la car)
        System.out.println(tax);

        Vehicle v2 = new Car();
        v2.move();

        if(car instanceof Cloneable) {
            Car c2;
            try {
                c2 = (Car) car.clone();
                c2.setCapacity(1900);
                System.out.println("Dacia capacity after c2.setCapacity(): " + car.getCapacity());
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
}
