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

        String minElement =
                list.stream()
                        .min(Comparator.comparing(Integer::parseInt))
                        .get();
        List<String> result = new LinkedList<>(list);
        result.remove(minElement);
        result.add(0, minElement);
        return result;
    }

    public static List<String> maxTwoSidedSort(List<String> list) {
        Integer maxElement =
                list.stream()
                        .max(Comparator.comparing(Integer::parseInt))
                        .map(Integer::parseInt)
                        .get();
        List<Integer> intList =
                list.stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        Collections.sort(intList.subList(0, intList.indexOf(maxElement)));
        Collections.sort(intList.subList(intList.indexOf(maxElement), intList.size()), Collections.reverseOrder());
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