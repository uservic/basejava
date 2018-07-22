package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapUuidStorage;
import ru.javawebinar.basejava.storage.Storage;

public class MainTestArrayStorage {
    //        static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();
//        static final Storage ARRAY_STORAGE = new ArrayStorage();
//    static final ListStorage ARRAY_STORAGE = new ListStorage();
    static final Storage ARRAY_STORAGE = new MapUuidStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid0", "Bob First");
        Resume r2 = new Resume("uuid5", "Ann Second");
        Resume r3 = new Resume("uuid3", "Zoe Third");
        Resume r4 = new Resume("uuid1", "Kid Fourth");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        System.out.println(ARRAY_STORAGE.size());

        printAll();
        String uuid1_Name = ARRAY_STORAGE.get("uuid1").getFullName();
        System.out.println("uuid0 name: " + uuid1_Name);

        Resume r5 = new Resume("uuid1", "Kid UpdatedName");
        ARRAY_STORAGE.update(r5);
        String uuid1_Name_Updated = ARRAY_STORAGE.get("uuid1").getFullName();
        System.out.println("uuid0 name: " + uuid1_Name_Updated);
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}