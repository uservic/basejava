package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String parse(LocalDate date) {
        if (date !=null ) {
            return date.equals(NOW) ? "Сейчас" : date.format(DATE_TIME_FORMATTER);
        } else {
            return "";
        }
    }

    public static LocalDate toShortDate(String date) {
        if (!"".equals(date)) {
            return YearMonth.parse(date, DATE_TIME_FORMATTER).atEndOfMonth();
        } else {
            return NOW;
        }
    }
}