package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class OrganisationData {
    private String title;
    private Link homePage;
    private final Map<LocalDate, String> map = new TreeMap<>(Comparator.reverseOrder());

    public OrganisationData(String title, Link homePage, LocalDate date, String content) {
        this.title = title;
        map.put(date, content);
        this.homePage = homePage;
    }

    public void addData(LocalDate date, String content) {
        map.put(date, content);
    }

    public String getOrganisationData() {
        return title + " " + homePage.getLink() + ": " + map.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationData that = (OrganisationData) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(homePage, that.homePage) &&
                Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, homePage, map);
    }
}
