package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<AdditionalContent> contentList = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String position, String positionDescription) {
        this.homePage = new Link(name, url);
        contentList.add(new AdditionalContent(startDate, endDate, position, positionDescription));
    }

    public void addOrganizationContent(LocalDate startDate, LocalDate endDate, String position, String positionDescription) {
        contentList.add(new AdditionalContent(startDate, endDate, position, positionDescription));
    }

    class AdditionalContent {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String position;
        private final String positionDescription;

        public AdditionalContent(LocalDate startDate, LocalDate endDate, String position, String positionDescription) {
            this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");;
            this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");;
            this.position = Objects.requireNonNull(position, "position must not be null");;
            this.positionDescription = positionDescription;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdditionalContent that = (AdditionalContent) o;
            return Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate) &&
                    Objects.equals(position, that.position) &&
                    Objects.equals(positionDescription, that.positionDescription);
        }

        @Override
        public int hashCode() {

            return Objects.hash(startDate, endDate, position, positionDescription);
        }


        @Override
        public String toString() {
            return  "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", position='" + position + '\'' +
                    ", positionDescription='" + positionDescription + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(contentList, that.contentList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homePage, contentList);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", contentList=" + contentList +
                '}';
    }
}