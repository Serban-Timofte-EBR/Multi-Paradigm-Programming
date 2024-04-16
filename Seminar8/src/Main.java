import java.util.Arrays;

//Să se realizeze înmulțirea unei matrice generice numerice cu un vector generic
//de același tip folosind fire de execuție.
class Main {
    public static void main(String[] args) {
        try {
            int n = 50;
            int m = 10;
            int NUMAR_FIRE = 6;

            Double[][] x = new Double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    x[i][j] = ((double)i) * m + j + 1;
                }
            }
            Double[] y = new Double[m], z = new Double[n];
            Arrays.fill(y, 1.0);
            Arrays.fill(z, 0.0);

            System.out.println("x:");
            for (int i = 0; i < n; i++) {
                System.out.println(Arrays.toString(x[i]));
            }
            System.out.println("y: " + Arrays.toString(y));

            int p = n/NUMAR_FIRE + (n%NUMAR_FIRE == 0 ? 0 : 1);
            Inmultire<Double>[] rFir = new Inmultire[NUMAR_FIRE];
            Thread[] fire = new Thread[NUMAR_FIRE];
            for (int i = 0; i < NUMAR_FIRE; i++) {
                int i1 = i * p;
                int i2 = Math.min((i+1)*p -1, n-1);

                rFir[i] = new Inmultire<>(x, y, z, i1, i2, new Operatiuni<Double>() {
                    @Override
                    public Double init() {
                        return 0.0;
                    }

                    @Override
                    public Double add(Double a, Double b) {
                        return a+b;
                    }

                    @Override
                    public Double inmultire(Double a, Double b) {
                        return a*b;
                    }
                });
                fire[i] = new Thread(rFir[i]);
                fire[i].start();
            }
            for (int i = 0; i < NUMAR_FIRE; i++) {
                fire[i].join();
            }
            System.out.println(Arrays.toString(z));
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
}