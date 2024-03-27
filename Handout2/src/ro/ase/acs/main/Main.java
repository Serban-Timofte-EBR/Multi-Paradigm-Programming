package ro.ase.acs.main;

import ro.ase.acs.models.EnGrosProduct;

public class Main {

	public static void main(String[] args) {
		//Playground
		//here you can test your code manually
		EnGrosProduct p1 = new EnGrosProduct("Bread", 20, 30);
		try {
			EnGrosProduct p2 = (EnGrosProduct) p1.clone();
			p1.setName("Watermelon");
			System.out.println(p2);
			System.out.println(p1.equals(p2));
		}
		catch (Exception e) {
			System.err.println(e);
		}

		System.out.println();
		System.out.println();

		EnGrosProduct flour = new EnGrosProduct("Flour", 16, 30);
		EnGrosProduct sugar = new EnGrosProduct("Sugar", 9, 16);
		EnGrosProduct flour2 = new EnGrosProduct("Flour", 16, 20);

		EnGrosProduct.addProduct(flour);
		EnGrosProduct.addProduct(sugar);
		EnGrosProduct.addProduct(flour2);

//		EnGrosProduct product1 = new EnGrosProduct("ABCD", 100.2F, 11);
//		EnGrosProduct product2 = new EnGrosProduct("ABCD", 100.7F, 15);
//		EnGrosProduct product3 = new EnGrosProduct("ABCD", 100.2F, 19);
//		EnGrosProduct product4 = new EnGrosProduct("XYZ", 50.0F, 10);
//		EnGrosProduct product5 = new EnGrosProduct("XYZ", 50.0F, 20);
//		EnGrosProduct.addProduct(product1);
//		EnGrosProduct.addProduct(product2);
//		EnGrosProduct.addProduct(product3);
//		EnGrosProduct.addProduct(product4);
//		EnGrosProduct.addProduct(product5);

		String list = EnGrosProduct.printStock();
		System.out.println(list);
	}
}
