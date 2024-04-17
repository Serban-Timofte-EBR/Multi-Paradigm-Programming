package ro.ase.acs.main;

import ro.ase.acs.classes.Car;
import ro.ase.acs.classes.Vehicle;
import ro.ase.acs.interfaces.Taxable;

import java.util.*;

public class Main {

    static void add(Car x, Car y) { //transmiterea parametrilor se face prin valoare, cand sunt referinte se pastreaza valaorea initiala daca nu modific ceva la valoarea initiala
        x = new Car(x.getName(), x.getSpeed(), x.getColor(), x.getCapacity());
        x.setCapacity(x.getCapacity() + y.getCapacity());
    }
    //set are elemente unice vs list

    //LIST: Vector, ArrayList, LinkedList
    //listele sunt optimizate pentru inserari oriunde
    //vectorii sunt optimi pentru inserari la sfarsit

    //SET: TreeSet, HashSet, LinkedHashSet
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

        Car c2 = null;
        if(car instanceof Cloneable) {
            try {
                c2 = (Car) car.clone();
                c2.setCapacity(1900);
                System.out.println("Dacia capacity after c2.setCapacity(): " + car.getCapacity());
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("-------------------------");
        System.out.println("Lab 4");
        System.out.println(car);

        List<Integer> list = new ArrayList<>();
        list.add(4);
        //list.add(3.4);
        list.add(5);
        list.add(6);

        for(int i=0; i< list.size(); i++) {
            System.out.println(list.get(i));
        }

        list.remove(2);
        list.add(1, 3);

        System.out.println();

        for (Integer val : list) {
            System.out.println(val);
        }

        list.set(1, 9);

        System.out.println();
        for(Iterator<Integer> it = list.iterator(); it.hasNext();){
            System.out.println(it.next());
        }

        System.out.println();
        System.out.println("Lab 5");
        Set<Car> set = new TreeSet<>();
        //c2.setCapacity(1400);
        set.add(car);
        set.add(c2);

        for(Car x: set) {
            System.out.println(x);
        }

        System.out.println();
        Map<Car, String> map = new HashMap<>();
        map.put(car, "Ionel Ionescu");
        map.put(c2, "Gigel Georgescu");
        Car c3 = (Car) car.clone();
        map.put(c3, "Pop Popescu");

        for(Car x : map.keySet()) {
            System.out.printf("%s : ", x.toString());
            System.out.println(map.get(x));
        }

        System.out.println();
        System.out.println("Car initial capacity: " + car.getCapacity());
        add(car, c2);
        System.out.println("Car final capacity: " + car.getCapacity());
    }
}

