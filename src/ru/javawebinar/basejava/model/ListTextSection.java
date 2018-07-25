package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection implements Section {
    private final List<String> list = new ArrayList<>();

    @Override
    public List<String> getContent() {
        return list;
    }

    @Override
    public void addContent(Object content) {
        list.add((String) content);
    }
}
