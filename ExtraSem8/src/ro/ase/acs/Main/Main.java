package ro.ase.acs.Main;

import ro.ase.acs.Classes.AsyncThread;
import ro.ase.acs.Classes.SyncedThread;

import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        //async - yeld concept
        //parallel - multicore
        AsyncThread t1 = new AsyncThread();
        AsyncThread t2 = new AsyncThread();
        //Race condition - both try to increment same resources
//        t1.start();
//        t2.start();
        SyncedThread t3 = new SyncedThread();
        t3.setName("T3");
        new Thread((t3)).start();               //because we implement Runable

        SyncedThread t4 =  new SyncedThread();
        new Thread((t4)).start();
        t4.setName("T4");

//        Runnable r = () -> {
//            System.out.println("Message from other thread");
//        };
//        new Thread(r).start();

        new Thread(() -> {
            System.out.println("Message from other thread");
        }).start();

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i+1);
        }

        long sum = list.parallelStream().mapToInt(i -> i).sum();
        System.out.println(sum);
        System.out.println("Main ended!");
    }
}