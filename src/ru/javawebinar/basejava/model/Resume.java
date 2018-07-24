package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private final Map<ContactType, TextSections> contacts = new HashMap<>();
    private final Map<SectionType, TextSections> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");

        contacts.put(ContactType.PHONE_NUMBER, new PlainTextSection());
        contacts.put(ContactType.SKYPE, new PlainTextSection());
        contacts.put(ContactType.EMAIL, new PlainTextSection());

        contacts.put(ContactType.WEB_SITES, new ListTextSection());

        sections.put(SectionType.OBJECTIVE, new PlainTextSection());
        sections.put(SectionType.PERSONAL, new PlainTextSection());

        sections.put(SectionType.ACHIEVEMENT, new ListTextSection());
        sections.put(SectionType.QUALIFICATIONS, new ListTextSection());

        sections.put(SectionType.EXPERIENCE, new CompoundTextSection());
        sections.put(SectionType.EDUCATION, new CompoundTextSection());
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public TextSections getContactByType(ContactType ct) {
        return contacts.get(ct);
    }

    public TextSections getSectionByType(SectionType st) {
        return sections.get(st);
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}