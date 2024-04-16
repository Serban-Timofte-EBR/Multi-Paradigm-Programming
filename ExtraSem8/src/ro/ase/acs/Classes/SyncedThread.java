package ro.ase.acs.Classes;

public class SyncedThread implements Runnable{
    private static Object locker = new Object();
    private static int a = 0;
    private static int b = 0;
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            synchronized (locker) {         //locker should be same type for class as the resources ( static in this case )
                System.out.println(name + " a = " + a + " b = " + b);
                a++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                b++;
            }
        }
    }
}
