public class Inmultire<T extends Number> implements Runnable{

    private T[][] x;
    private T[] y,z;    // x se inmulteste cu vectorul y si rezulta z
    private int i1,i2;

    private Operatiuni<T> operatiuni;

    public Inmultire(T[][] x, T[] y, T[] z, int i1, int i2, Operatiuni<Double> operatiuni) throws Exception {
        if(x.length != z.length || x[0].length != y.length) {
            throw new Exception("Dimensiuni incorecte!");
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.i1 = i1;
        this.i2 = i2;
    }

    @Override
    public void run() {
        for (int i = i1; i <= i2; i++) {
            z[i] = operatiuni.init();
            for (int j = 0; j < y.length; j++) {
                z[i] = operatiuni.add(z[i], operatiuni.inmultire(x[i][j], y[j]));
            }
        }
    }
}
