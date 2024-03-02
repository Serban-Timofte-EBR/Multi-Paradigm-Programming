package ro.ase.acs.main;

import ro.ase.acs.classes.Car;

public class Main {
    public static void main(String[] args) {
        Car c1 = new Car("Tesla", 40000);   //fueltype by default este null
        System.out.println(c1.getProducer());

        Car c2 = c1.clone();
        c1.setPrice(30000);
        c1.setProducer("Honda");
        System.out.println("C2 price after modifing c1 price: " + c2.getPrice());
        System.out.println("C2 name after modifing c1 producer: " + c1.getProducer());

        int[] vector = new int[] {100, 200, 150};
        c1.setDistance(vector);
        vector[0] = 5000;

        int[] distancesC1 = c1.getDistance();
        distancesC1[0] = 8000;
        for(int dist:distancesC1) {
            System.out.println(dist);
        }

        String s = "something";
        s = "something else";   //s este imutabil si acum fac un nou s
        System.out.println(s);
    }
}
