package tema7_1.Main;

import tema7_1.Classes.Student;
import tema7_1.Classes.Subsir;

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int n = 100;
        Double[] x = new Double[n];
        for (int i = 0; i < n; i++) {
            x[i] = Math.random() * n;
        }
        int m = n/2;
        Subsir<Double> subSir1 = new Subsir<>(x, 0, m);
        Subsir<Double> subsir2 = new Subsir<>(x, m+1, n-1);
        Thread fir1 = new Thread(subSir1), fir2 = new Thread(subsir2);
        fir1.start();
        fir2.start();
        try {
            fir1.join();
            fir2.join();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        System.out.println(Arrays.toString(x));
    }
}
