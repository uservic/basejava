package ru.javawebinar.basejava.util;

import org.junit.Test;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.TestData.RESUME_ONE;
import static ru.javawebinar.basejava.TestData.RESUME_THREE;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_ONE);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_ONE, resume);
    }

    @Test
    public void write() throws Exception {
        Section section = new ListSection("item_1", "item_2", "item_1");
        String json = JsonParser.write(section, Section.class);
        System.out.println(json);
        assertEquals(section, JsonParser.read(json, Section.class));
    }
}