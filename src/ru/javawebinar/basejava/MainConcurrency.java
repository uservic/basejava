package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
//    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(SimpleDateFormat::new);
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

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                latch.countDown();
                return 5;
            });
        }

        /*threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        latch.await();
//        System.out.println(counter);
        System.out.println(atomCounter.get());
        executorService.shutdown();
//        System.out.println(atomCounter);
    }

    private static void inc() {
//        synchronized (LOCK) {
//        lock.lock();
//        try {
//            counter++;
        atomCounter.incrementAndGet();
//        } finally {
//            lock.unlock();
//        }
//            LOCK.wait();
//        }
    }
}