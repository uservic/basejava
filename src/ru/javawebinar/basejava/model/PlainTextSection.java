package ru.javawebinar.basejava.model;

public class PlainTextSection implements Section {
    private String content;

    public PlainTextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void addContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}