package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);
    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public String getOrgName() {
        return homePage.getName();
    }

    public String getOrgUrl() {
        return homePage.getUrl();
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + "," + positions + ")";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        public static final Position EMPTY = new Position();
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String position;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String position, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, position, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String position, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), position, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String position, String description) {
            this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
            this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
            this.position = Objects.requireNonNull(position, "position must not be null");
            this.description = description == null ? "" : description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return Objects.equals(startDate, position1.startDate) &&
                    Objects.equals(endDate, position1.endDate) &&
                    Objects.equals(position, position1.position) &&
                    Objects.equals(description, position1.description);
        }

        @Override
        public int hashCode() {

            return Objects.hash(startDate, endDate, position, description);
        }

        @Override
        public String toString() {
            return "Position(" + startDate + "," + endDate + "," + position + "," + description + ")";
        }
    }
}