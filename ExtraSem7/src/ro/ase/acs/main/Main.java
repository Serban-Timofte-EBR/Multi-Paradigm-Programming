package ro.ase.acs.main;

import ro.ase.acs.classes.Addtion;
import ro.ase.acs.interfaces.BinaryOperation;
import ro.ase.acs.interfaces.Printable;
import ro.ase.acs.interfaces.Taxable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //refference to the interface ( is null )
        BinaryOperation op;
        op = new Addtion(); //upcasting ( interface as a type )
        System.out.println(op.compute(2, 4.3));
        System.out.println(op.compute(2, 3));

        //Anonymous Object
        double result = new Addtion().compute(2, 4);
        System.out.println(result);

        //Anonymus Class
        op = new BinaryOperation() {
            @Override
            public double compute(double x, double y) {
                return x * y;
            }
        };
        System.out.println(op.compute(4, 5));

        //Override the compute from BinaryOperation ( works only on functionally interface )
        op = (x,y) -> x / y;
        System.out.println(op.compute(4 ,5));

        Printable prt = message -> System.out.println(message);
        prt.print("Hello, World!");

        double value = 100;
//        Taxable tx = () -> value * 0.2;
//        Taxable tx = () -> value < 0 ? 0 : value*0.2;
        Taxable tx = () -> {
            if(value < 0) {return 0;}
            else{
                return value * 0.2;
            }
        };
        System.out.println(tx.computeTax());

        List<Integer> listOfInt = List.of(1, 3 , 5, 1, 2, 4, 8, 8, 10);
        long nrDist = listOfInt.stream().distinct().count();
        System.out.println(nrDist);

        //contains even numbers sorted
        System.out.println("Sublist with even numbers sorted!");
        List<Integer> subList = listOfInt.stream().filter(nr -> nr%2 == 0).sorted().toList();
        subList.forEach(System.out::println);

        List<String> names = List.of("Maria", "John", "George", "Elizabeth", "Charles");
//        String namesUpperComma = names.stream().map(name -> name.toUpperCase()).sorted()
//                .reduce((name1, name2) -> name1 + ", " + name2).get();
        String namesUpperComma = names.stream().map(String::toUpperCase).sorted()
                        .reduce((name1, name2) -> name1 + ", " + name2).get();
        System.out.println(namesUpperComma);

        names.forEach(System.out::println);
    }
}