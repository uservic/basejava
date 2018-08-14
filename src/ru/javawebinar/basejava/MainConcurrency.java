package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
    private static final Object LOCK = new Object();
    private static AtomicInteger atomCounter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
        System.out.println(thread0.getState());

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
//                    atomCounter.incrementAndGet();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
//        System.out.println(atomCounter);
    }

    private synchronized static void inc() {
//        synchronized (LOCK) {
            counter++;
//            LOCK.wait();
//        }
    }
}