public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        //Student s;      //in Java, clasele sunt de tip referential => s este null, nu se utilizeaza constructorul default
        Student s = new Student();
        System.out.println(s.getGrade());

        Student s2 = new Student("Ionel", 9.5f);
        System.out.println(s2.getName());
        System.out.println(s2.getGrade());

        Student s3 = s2;        //am copiat adresa de memorie din s2 in s3 => pointeaza in aceeasi zona din HEAP
        s3.setName("Gigel");
        System.out.println("S2 get name dupa s3.setName");
        System.out.println(s2.getName());

        Student s3_clone = s2.myClone();
        s3_clone.setName("popescu");
        System.out.println("S3_clone dupa s3_clone.setName()");
        System.out.println(s3_clone.getName());
        System.out.println("S2 dupa s3_clone.setName()");
        System.out.println(s2.getName());

        //Tipuri de date in Java:
            //Referentiale: Clase + 2 tipuri (String este clasa - valoare se salveaza in HEAP, adresa lor se salveaza in stack)
            //Primitive: tipuri de date de tipul int, long, short, char, bool, etc
            //Primitivele au valori implicite normale ( 0 sau false ), iar cele referentiale cu null

    }
}