package tema7_1.Classes;

import java.util.Arrays;

public class Subsir<T> implements Runnable{
    private T[] x;
    private int p, u;

    public Subsir(T[] x, int p, int u) {
        this.x = x;
        this.p = p;
        this.u = u;
    }

    @Override
    public void run() {
        sortare();
    }

    public void sortare() {
        Arrays.sort(x, p, u+1);
    }
}
