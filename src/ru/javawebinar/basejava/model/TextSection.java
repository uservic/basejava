package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends Section {

    private static final long serialVersionUID = 1L;
    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        this.content = Objects.requireNonNull(content, "content must not be null");;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    public String getContent() {
        return content;
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