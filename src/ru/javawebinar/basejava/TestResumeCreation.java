package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");
        System.out.println();
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + ": " + resume.getContactByType(contactType));
        }
        System.out.println("--------------------------");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + resume.getSectionByType(sectionType).getContent());
        }
    }
}