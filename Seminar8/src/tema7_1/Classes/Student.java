package tema7_1.Classes;

public class Student {
    private final String nume;
    private final double medie;

    public Student(String nume, double medie) {
        this.nume = nume;
        this.medie = medie;
    }

    public String getNume() {
        return nume;
    }

    public double getMedie() {
        return medie;
    }
}
