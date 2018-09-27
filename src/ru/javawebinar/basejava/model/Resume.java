package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    public static final Resume EMPTY_RESUME = makeEmptyResume();

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public static Resume makeEmptyResume() {
        Resume resume = new Resume();
        resume.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        resume.setSection(SectionType.PERSONAL, TextSection.EMPTY);
        resume.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        resume.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
        return resume;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getContact(ContactType ct) {
        return contacts.get(ct);
    }

    public Section getSection(SectionType st) {
        return sections.get(st);
    }

    public void setContact(ContactType ct, String contact) {
        contacts.put(ct, contact);
    }

    public void setSection(SectionType st, Section section) {
        sections.put(st, section);
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
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}