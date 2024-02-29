public class Agent {
    private long cnp;
    private String nume;
    private int[] imobile;      //Lucram separat cu int[], nu includem in constructor

    // Existenta constructorului default este ca in C++, daca este alt constructor nu mai este un constructor default
    public Agent() {
    }

    public Agent(long cnp, String nume) {
        this.cnp = cnp;
        this.nume = nume;
    }

    public Agent(long cnp) {
        this.cnp = cnp;
    }
}
