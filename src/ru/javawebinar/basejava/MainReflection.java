package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume r = new Resume("someName");
        Method method = r.getClass().getMethod("toString");
        String s = (String) method.invoke(r);
        System.out.println(s);
    }
}