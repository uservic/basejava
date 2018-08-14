package ru.javawebinar.basejava.util;

public class LazySingleton {

//    private volatile static LazySingleton INSTANCE = null;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getINSTANCE() {
        return LazySingletonHolder.INSTANCE;
    }

    //    public static synchronized LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    int i = 13;
//                    return new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}