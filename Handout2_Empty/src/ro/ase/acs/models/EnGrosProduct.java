package ro.ase.acs.models;

public class EnGrosProduct extends  Product implements Cloneable{
    private float discount;

    public EnGrosProduct(String name, float price, int quantity) {
        super(name, price, quantity);
        if(quantity < 10) {this.discount = 0;} else if (quantity >= 10 && quantity < 20) {
            this.discount = 0.05f;
        } else {this.discount = 0.1f;}
    }
    @Override
    public float getDiscount() {
        return discount * price;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        EnGrosProduct copy = (EnGrosProduct) super.clone();
        copy.price = price;
        copy.name = name;
        copy.quantity = quantity;
        copy.discount = discount;
        return copy;
    }
}
