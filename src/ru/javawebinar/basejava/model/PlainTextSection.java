package ru.javawebinar.basejava.model;

public class PlainTextSection implements TextSections {
    private String content = "no data";

    @Override
    public String getContent() {
        return content;
    }

    public void addContent(String content) {
        this.content = content;
    }
}
