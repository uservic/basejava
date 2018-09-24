package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String parse(LocalDate date) {
        if (date !=null ) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
            return date.equals(NOW) ? "Сейчас" : date.format(dtf);
        } else {
            return "";
        }
    }

    public static LocalDate toShortDate(String date) {
        if (!"".equals(date)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
            return YearMonth.parse(date, dtf).atEndOfMonth();
        } else {
            return null;
        }
    }
}