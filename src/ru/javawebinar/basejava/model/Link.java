package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Link implements Section {
    private String title;
    private String link;

    public Link(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link1 = (Link) o;
        return Objects.equals(title, link1.title) &&
                Objects.equals(link, link1.link);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, link);
    }

    @Override
    public String toString() {
        return title + ": " + link;
    }
}