package ro.ase.acs.models;

import ro.ase.acs.contracts.Saleable;

public abstract class Product implements Saleable, Cloneable {
    protected String name;
    protected float price;
    protected int quantity;

    public Product(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public float getPrice() {
        return this.price;
    }

    public abstract float getDiscount();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Product copy = (Product) super.clone();
        copy.name = name;
        copy.price = price;
        copy.quantity = quantity;
        return  copy;
    }
}
