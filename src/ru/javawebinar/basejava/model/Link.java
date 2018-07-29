package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Link {
    private String name;
    private String url;

    public Link(String name, String url) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link1 = (Link) o;
        return Objects.equals(name, link1.name) &&
                Objects.equals(url, link1.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "Link(" + name + ", " + url + ")";
    }
}