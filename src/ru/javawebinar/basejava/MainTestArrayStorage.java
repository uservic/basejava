package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for com.urise.webapp.storage.ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    //    static final Storage ARRAY_STORAGE = new ArrayStorage();
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid9");
        Resume r2 = new Resume("uuid5");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid1");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        printAll();

        ARRAY_STORAGE.delete(r3.getUuid());
//        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.delete(r2.getUuid());
        ARRAY_STORAGE.delete(r4.getUuid());

//        Resume rX = new Resume();
//        rX.setUuid("uuid-1");
//        ARRAY_STORAGE.save(rX);
//
//        Resume rY = new Resume();
//        rY.setUuid("uuid0");
//        ARRAY_STORAGE.save(rY);
//
//        Resume rZ = new Resume();
//        rZ.setUuid("uuid8");
//        ARRAY_STORAGE.save(rZ);

//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
////        System.out.println("Size: " + ARRAY_STORAGE.size());
////
//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
////
        printAll();
//
////
//        ARRAY_STORAGE.delete(r1.getUuid());
//
//        ARRAY_STORAGE.delete(r3.getUuid());
//        printAll();
////        Resume r5 = new Resume();
////        r4.setUuid("uuid5");
////        ARRAY_STORAGE.save(r4);
////        printAll();
////        ARRAY_STORAGE.delete(r2.getUuid());
////
//        printAll();
//        ARRAY_STORAGE.clear();
//        printAll();
//
//        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
