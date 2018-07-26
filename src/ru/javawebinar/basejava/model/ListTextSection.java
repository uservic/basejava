package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection implements Section {
    private final List<String> list = new ArrayList<>();

    public List<String> getContent() {
        return list;
    }

    public void addContent(String content) {
        list.add(content);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}