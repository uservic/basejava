package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        Thread t1 = getThread(o1, o2);
        Thread t2 = getThread(o2, o1);

        t1.start();
        t2.start();
    }

    private static Thread getThread(Object o1, Object o2) {
       return new Thread(() -> {
           synchronized (o1) {
               System.out.println("some code1");
               synchronized (o2) {
                   System.out.println("some code2");
               }
           }
       });
    }
}