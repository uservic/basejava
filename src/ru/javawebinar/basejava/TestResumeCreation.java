package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSections;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

        TextSections phoneData = resume.getContactByType(ContactType.PHONE_NUMBER);
        System.out.println(ContactType.PHONE_NUMBER.getTitle() + ": " + phoneData.getContent());
        phoneData.addContent("+7 921 123 45 67");
        System.out.println(ContactType.PHONE_NUMBER.getTitle() + ": " + phoneData.getContent());

        System.out.println("--------------------------");

        TextSections skypeData = resume.getContactByType(ContactType.SKYPE);
        System.out.println(ContactType.SKYPE.getTitle() + ": " + skypeData.getContent());
        skypeData.addContent("skype.address");
        System.out.println(ContactType.SKYPE.getTitle() + ": " + skypeData.getContent());

        System.out.println("--------------------------");

        TextSections emailData = resume.getContactByType(ContactType.EMAIL);
        System.out.println(ContactType.EMAIL.getTitle() + ": " + emailData.getContent());
        emailData.addContent("dummy@mail.com");
        System.out.println(ContactType.EMAIL.getTitle() + ": " + emailData.getContent());

        System.out.println("--------------------------");

        TextSections linksData = resume.getContactByType(ContactType.WEB_SITES);
        System.out.println(ContactType.WEB_SITES + ": " + linksData.getContent());
        linksData.addContent("www.google.com");
        linksData.addContent("www.yandex.com");
        linksData.addContent("www.bing.com");
        System.out.println(ContactType.WEB_SITES + ": " + linksData.getContent());

        System.out.println("--------------------------");

        TextSections objectiveData = resume.getSectionByType(SectionType.OBJECTIVE);
        System.out.println(SectionType.OBJECTIVE.getTitle() + ": " + objectiveData.getContent());
        objectiveData.addContent("position content");
        System.out.println(SectionType.OBJECTIVE.getTitle() + ": " + objectiveData.getContent());

        System.out.println("--------------------------");

        TextSections personalData = resume.getSectionByType(SectionType.PERSONAL);
        System.out.println(SectionType.PERSONAL.getTitle() + ": " + personalData.getContent());
        personalData.addContent("personal content");
        System.out.println(SectionType.PERSONAL.getTitle() + ": " + personalData.getContent());

        System.out.println("--------------------------");

        TextSections achievementData = resume.getSectionByType(SectionType.ACHIEVEMENT);
        System.out.println(SectionType.ACHIEVEMENT.getTitle() + ": " + achievementData.getContent());
        achievementData.addContent("achievement content1");
        achievementData.addContent("achievement content2");
        achievementData.addContent("achievement content3");
        System.out.println(SectionType.ACHIEVEMENT.getTitle() + ": " + achievementData.getContent());

        System.out.println("--------------------------");

        TextSections qualificationsData = resume.getSectionByType(SectionType.QUALIFICATIONS);
        System.out.println(SectionType.QUALIFICATIONS.getTitle() + ": " + qualificationsData.getContent());
        qualificationsData.addContent("qualifications content1");
        qualificationsData.addContent("qualifications content2");
        qualificationsData.addContent("qualifications content3");
        System.out.println(SectionType.QUALIFICATIONS.getTitle() + ": " + qualificationsData.getContent());

        System.out.println("--------------------------");

        TextSections experienceData = resume.getSectionByType(SectionType.EXPERIENCE);
        System.out.println(SectionType.EXPERIENCE.getTitle() + ": " + experienceData.getContent());
        experienceData.addContent("experience title1");
        experienceData.addContent("experience title2");
        experienceData.addContent("experience title3");
        System.out.println(SectionType.EXPERIENCE.getTitle() + ": " + experienceData.getContent());

        System.out.println("--------------------------");

        TextSections educationData = resume.getSectionByType(SectionType.EDUCATION);
        System.out.println(SectionType.EDUCATION.getTitle() + ": " + educationData.getContent());
        educationData.addContent("education title1");
        educationData.addContent("education title2");
        educationData.addContent("education title3");
        System.out.println(SectionType.EDUCATION.getTitle() + ": " + educationData.getContent());

        System.out.println("--------------------------");
    }
}