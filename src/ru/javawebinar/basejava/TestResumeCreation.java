package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.Month;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 921 123 45 67").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("AnnOneskype.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("ann@mail.com").toString());

        Link linkMySite = new Link("HomePage", "www.mysite.com");
        resume.addContact(ContactType.HOME_PAGE, linkMySite.getUrl());

        Link linkMyLinkedIn = new Link("LinkedIn", "www.lnkd/AnnOne.com");
        resume.addContact(ContactType.HOME_PAGE, linkMyLinkedIn.getUrl());

        resume.addSection(SectionType.OBJECTIVE, new TextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("achievement content1", "achievement content2"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("qualifications content1", "qualifications content2"));

        Organization organisationData1 = new Organization(
                "Organisation1",
                "www.org1.com",
                new Organization.Position(2009, Month.JANUARY, 2010, Month.JANUARY, "org1_pos", "org1_posDescr"),
                new Organization.Position(2010, Month.JANUARY, 2012, Month.JUNE, "org1_pos2", "org1_posDescr2"));

        Organization organisationData2 = new Organization(
                "Organisation2",
                "www.org2.com",
                new Organization.Position(2012, Month.JULY, "org2_pos", "org2_posDescr"));

        resume.addSection(SectionType.EXPERIENCE, new OrganizationtSection(organisationData1, organisationData2));

        Organization universityData1 = new Organization(
                "University1",
                "www.uni1.com",
                new Organization.Position(2000, Month.SEPTEMBER, 2005, Month.JUNE, "student", null));

        Organization universityData2 = new Organization(
                "University2",
                "www.uni2.com",
                new Organization.Position(2005, Month.SEPTEMBER, 2007, Month.JUNE, "student", null));

        resume.addSection(SectionType.EDUCATION, new OrganizationtSection(universityData1, universityData2));

        System.out.println();
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + ": " + resume.getContact(contactType));
        }
        System.out.println("--------------------------");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + resume.getSection(sectionType).toString());
        }
    }
}