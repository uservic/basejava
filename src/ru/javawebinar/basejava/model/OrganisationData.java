package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class OrganisationData {
    private String title;
    private Link homePage;
    private LocalDate date;
    private String position;
    private String positionDescription;

    public OrganisationData(String title, Link homePage, LocalDate date, String position, String positionDescription) {
        this.title = title;
        this.homePage = homePage;
        this.date = date;
        this.position = position;
        this.positionDescription = positionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationData that = (OrganisationData) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(homePage, that.homePage) &&
                Objects.equals(date, that.date) &&
                Objects.equals(position, that.position) &&
                Objects.equals(positionDescription, that.positionDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, homePage, date, position, positionDescription);
    }

    @Override
    public String toString() {
        return title + " " + homePage.getLink() + " " + date + " " + position + " " + positionDescription;
    }
}