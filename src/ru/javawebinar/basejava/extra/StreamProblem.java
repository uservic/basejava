package ru.javawebinar.basejava.extra;

import java.util.Arrays;

public class StreamProblem {

    public static void main(String[] args) {
        int[] values1 = new int[]{1, 2, 3, 3, 2, 3};
        int[] values2 = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] values3 = new int[]{5, 5, 5, 5, 5};

        System.out.println(minValue(values1));
        System.out.println(minValue(values2));
        System.out.println(minValue(values3));
    }

    public static int minValue(int[] values) {
        return Arrays
                .stream(values)
                .distinct()
                .sorted()
                .reduce((number1, number2) -> number1 * 10 + number2).getAsInt();
    }
}