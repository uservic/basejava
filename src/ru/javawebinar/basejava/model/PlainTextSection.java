package ru.javawebinar.basejava.model;

public class PlainTextSection implements Section {
    private String content;

    public PlainTextSection(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void addContent(Object content) {
        this.content =  (String) content;
    }
}