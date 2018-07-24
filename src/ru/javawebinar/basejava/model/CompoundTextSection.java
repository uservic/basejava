package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CompoundTextSection implements TextSections {
    private final Map<String, Map<LocalDate, String>> map;

    public CompoundTextSection() {
        map = new HashMap<>();
        String titleOfSection = "No title";
        Map<LocalDate, String> sectionMap = new HashMap<>();
        sectionMap.put(LocalDate.now(), "no data");
        map.put(titleOfSection, sectionMap);
    }

    @Override
    public Map<String, Map<LocalDate, String>> getContent() {
        return map;
    }

    @Override
    public void addContent(String titleOfSection) {
        if (map.containsKey(titleOfSection)) {
            map.get(titleOfSection).put(LocalDate.now(), "some text");
        } else {
            Map<LocalDate, String> subSection = new HashMap<>();
            subSection.put(LocalDate.now(), "some text");
            map.put(titleOfSection, subSection);
        }
    }
}
