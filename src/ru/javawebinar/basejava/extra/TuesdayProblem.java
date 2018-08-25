package ru.javawebinar.basejava.extra;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TuesdayProblem {
    public static void main(String[] args) {
//        String date = LocalDate.now().toString();
        String date = LocalDate.of(2018, 8, 10).toString();
        System.out.printf("current date --> %s \nprevious Tuesday date --> %s", date, getPrevTuesday(date) );
    }

    public static LocalDate getPrevTuesday(String date) {
        LocalDate ld = LocalDate.parse(date);
        return ld.with(TemporalAdjusters.previous(DayOfWeek.TUESDAY));
    }
}