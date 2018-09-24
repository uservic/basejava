package ru.javawebinar.basejava.util;

public class HtmlUtil {
    public static boolean isEmpty(String value) {
        return (value == null) | "null".equals(value) | (value.trim().length() == 0);
    }
}