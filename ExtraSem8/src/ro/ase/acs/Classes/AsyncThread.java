package ro.ase.acs.Classes;

import java.sql.Struct;

public class AsyncThread extends Thread {
    private static int a = 0;
    private static int b = 0;

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            //System.out.println("Message from other thread!");
            System.out.println("a = " + a + " b = " + b);
            // Threads has their own cache, so sometimes the threads are using the cache,
            // not the statci variabiles because there is a race condition on resources
                // Threads are not awared by each other
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