package ro.ase.acs.models;

import java.util.ArrayList;
import java.util.List;

public class EnGrosProduct extends  Product implements Cloneable{
    private static List<EnGrosProduct> shoppingList = new ArrayList<>();
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
    public Object clone() throws CloneNotSupportedException {
        EnGrosProduct copy = (EnGrosProduct) super.clone();
        copy.discount = discount;
        return copy;
    }

    @Override
    public String toString() {
        return "> " + name + " " + price + " " + quantity + " " + (int)(discount*100) + "% " + price * quantity +
                " " + (price * quantity - discount * (price * quantity));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {return true;}
        if(obj == null || getClass() != obj.getClass()) {return false;}

        EnGrosProduct param = (EnGrosProduct) obj;

        if(param.name.equals(this.name) && param.price == this.price) {return true;}

        return false;
    }

    public static void addProduct(EnGrosProduct product) {
        boolean found = false;
        for (EnGrosProduct existingProduct: shoppingList) {
            if(existingProduct.equals(product)) {
                existingProduct.quantity += product.quantity;
                found = true;
                break;
            }
        }
        if(!found) {
            shoppingList.add(product);
        }
        else {

        }
    }

    public static String printStock() {
        String list = "";
        float total_value = 0;
        for (EnGrosProduct prod: shoppingList) {
            list = list + prod.toString() + "\n";
            total_value += prod.price * prod.quantity;
        }
        list += "\nTotal value of the stock without discount: " + total_value;
        return list;
    }
}
