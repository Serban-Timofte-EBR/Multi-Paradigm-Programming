package ro.ase.acs.main;

import ro.ase.acs.classes.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

// Sem. 4 - Prof Iancu - 14 Martie
// Generic Classes = Template
public class Main {
    public static void main(String[] args) {
        System.out.println(eq("abc", "abc"));
        System.out.println(eq(21, 21));
        System.out.println(eq("av", "sa"));
        System.out.println(eq("ab", 21));   // T is object in the first moment

        System.out.println();

        System.out.println("Less then");
        System.out.println(lt("abc", "bef"));
        System.out.println(lt("ABC", "abc"));

        // Cannot use for classes that not implement Comparables
        /*
        Student s1 = new Student();
        Student s2 = new Student();
        lt(s1, s2);
         */

        //use it from java.util
        // List<int> integers; INT is a primitive so it is not an Object
        List<Integer> list = new ArrayList<>();
        list.add(21);
        list.add(9);
        list.add(11);
        list.add(2, 999);

        System.out.println(list);

        //If we need to modify it
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        Student s1 = new Student("Ionel", 10);
        Student s2 = new Student("Gigel", 9.99f);
        System.out.println(s1); //location without toSting implemented

        System.out.println("\n----------------------\n");

        List<Student> vector = new Vector<>();
        vector.add(s1);
        vector.add(s2);

        for (Student s:vector) {
            System.out.println(s);
        }

        for(Iterator<Student> it = vector.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

    public static <T> boolean eq(T val1, T val2) {
        return val1.equals(val2);       // == just compare the adresses => use equals to compare the values
    }

    public static <T extends Comparable> boolean lt(T val1, T val2) {
        return val1.compareTo(val2) < 0 ;   //compare to return -1, 0 or 1, using <0 transform the return as boolean
    }

}