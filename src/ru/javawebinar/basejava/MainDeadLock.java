package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1) {
                    System.out.println("some code1");
                    synchronized (o2) {
                        System.out.println("some code2");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2) {
                    System.out.println("some code3");
                    synchronized (o1) {
                        System.out.println("some code4");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
