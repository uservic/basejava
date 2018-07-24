package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection implements TextSections {
    private final List<String> list = new ArrayList<>();

    @Override
    public List<String> getContent() {
        return list;
    }

    @Override
    public void addContent(String content) {
        list.add(content);
    }
}
