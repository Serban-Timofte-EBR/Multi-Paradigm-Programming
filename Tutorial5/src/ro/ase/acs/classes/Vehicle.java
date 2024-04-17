package ro.ase.acs.classes;

//Tipuri de date in Java:
//primitive: int
//referentiale ( stocheaza o adresa in stiva, iar in heap este alocat spatiu de memorie )
//class, interface, enum

public abstract class Vehicle implements Cloneable{ //nu pot instantia, dar pot referentia
    private String name;
    private int speed;

    public Vehicle() {
        this.name = "";
        this.speed = 1;
    }

    public Vehicle(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    //functie abstracta pentru a face o clasa abstracta
    public abstract void move();

    @Override
    public Object clone() throws CloneNotSupportedException {
        Vehicle copy = (Vehicle) super.clone();
        copy.name = name;
        copy.speed = speed;
        return copy;
    }
}