package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        Cat cat = new Cat("Tom", 1, null);
        Mouse mouse = new Mouse("Jerry", 1, cat);
        cat.setMouse(mouse);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                cat.printCatName();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                mouse.printMouseName();
            }
        });
        t1.start();
        t2.start();
    }
}

class Cat {
    private String name;
    private int age;
    private Mouse mouse;

    public Cat(String name, int age, Mouse mouse) {
        this.name = name;
        this.age = age;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public synchronized void printCatName() {
        System.out.println(name);
        mouse.printMouseAge();
    }

    public synchronized void printCatAge() {
        System.out.println(age);
    }
}

class Mouse {
    private String name;
    private int age;
    private Cat cat;

    public Mouse(String name, int age, Cat cat) {
        this.name = name;
        this.age = age;
        this.cat = cat;
    }

    public synchronized void printMouseName() {
        System.out.println(name);
        cat.printCatAge();
    }

    public synchronized void printMouseAge() {
        System.out.println(age);
    }
}
