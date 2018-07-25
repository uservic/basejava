package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class OrganisationData {
    private String title;
    private final Map<LocalDate, String> map = new TreeMap<>(Comparator.reverseOrder());

    public OrganisationData(String title, LocalDate date, String content) {
        this.title = title;
        map.put(date, content);
    }

    public void addData(LocalDate date, String content) {
        map.put(date, content);
    }

    public String getOrganisationData() {
        return title + ": " + map.toString();
    }
}
