package ru.javawebinar.basejava.extra;

import java.util.*;
import java.util.stream.Collectors;

public class HundredStringNumbersProblem {

    public static void main(String[] args) {
//        List<String> list = Arrays.asList("5", "15", "8", "25", "2", "9", "1");
        List<String> list = Arrays.asList("5", "15", "1", "8", "25", "2", "9", "1");
//        List<String> list = Arrays.asList("5", "15", "1", "8", "25", "2", "1");
//        List<String> list = Arrays.asList("1", "1", "1");
        System.out.println(moveMinStart(list));
        System.out.println(maxTwoSidedSort(list));
        System.out.println(oddOrEven(list));
    }

    public static List<String> moveMinStart(List<String> list) {
        List<String> result = new LinkedList<>(list);
        list.stream()
                .min(Comparator.comparing(Integer::parseInt))
                .ifPresent(min -> {
                    result.remove(min);
                    result.add(0, min);
                });
        return result;
    }

    public static List<String> maxTwoSidedSort(List<String> list) {
//        Integer maxElement =
//                list.stream()
//                        .max(Comparator.comparing(Integer::parseInt))
//                        .map(Integer::parseInt)
//                        .get();
        List<Integer> intList =
                list.stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        Integer max = intList.get(0);
        int maxIndex = 0;
        for (int i = 0; i < intList.size(); i++) {
            Integer number = intList.get(i);
            if (number > max) {
                max = number;
                maxIndex = i;
            }
        }

        Collections.sort(intList.subList(0, maxIndex));
        Collections.sort(intList.subList(maxIndex, intList.size()), Collections.reverseOrder());
        return intList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public static List<String> oddOrEven(List<String> list) {
        int sum = list.stream().map(Integer::valueOf).reduce(0, (a, b) -> a + b);
        return list.stream()
                .map(Integer::parseInt)
                .filter(x -> sum % 2 == 0 ? x % 2 != 0 : x % 2 == 0)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}