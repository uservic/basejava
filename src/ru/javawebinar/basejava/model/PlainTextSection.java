package ru.javawebinar.basejava.model;

import java.util.Objects;

public class PlainTextSection implements Section {
    private String content;

    public PlainTextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainTextSection that = (PlainTextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}