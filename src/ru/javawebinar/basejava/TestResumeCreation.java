package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

//        resume.setContact(ContactType.PHONE_NUMBER, new TextSection("+7 921 123 45 67").toString());
//        resume.setContact(ContactType.SKYPE, new TextSection("AnnOneskype.address").toString());
//        resume.setContact(ContactType.EMAIL, new TextSection("ann@mail.com").toString());
//
//        Link linkMySite = new Link("HomePage", "www.mysite.com");
//        resume.setContact(ContactType.HOME_PAGE, linkMySite.getUrl());
//
//        Link linkMyLinkedIn = new Link("LinkedIn", "www.lnkd/AnnOne.com");
//        resume.setContact(ContactType.HOME_PAGE, linkMyLinkedIn.getUrl());
//
//        resume.setSection(SectionType.OBJECTIVE, new TextSection("position content"));
//        resume.setSection(SectionType.PERSONAL, new TextSection("personal content"));

        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("achievement content1", "achievement content2"));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("qualifications content1", "qualifications content2"));

//        Organization organisationData1 = new Organization(
//                "Organisation1",
//                "www.org1.com",
//                new Organization.Position(2009, Month.JANUARY, 2010, Month.JANUARY, "org1_pos", "org1_posDescr"),
//                new Organization.Position(2010, Month.JANUARY, 2012, Month.JUNE, "org1_pos2", "org1_posDescr2"));
//
//        Organization organisationData2 = new Organization(
//                "Organisation2",
//                "www.org2.com",
//                new Organization.Position(2012, Month.JULY, "org2_pos", "org2_posDescr"));
//
//        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(organisationData1, organisationData2));
//
//        Organization universityData1 = new Organization(
//                "University1",
//                "www.uni1.com",
//                new Organization.Position(2000, Month.SEPTEMBER, 2005, Month.JUNE, "student", null));
//
//        Organization universityData2 = new Organization(
//                "University2",
//                "www.uni2.com",
//                new Organization.Position(2005, Month.SEPTEMBER, 2007, Month.JUNE, "student", null));
//
//        resume.setSection(SectionType.EDUCATION, new OrganizationSection(universityData1, universityData2));

        System.out.println();
//        for (ContactType contactType : ContactType.values()) {
//            System.out.println(contactType.getTitle() + ": " + resume.getContact(contactType));
//        }
        System.out.println("--------------------------");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + resume.getSection(sectionType).toString());
        }
    }
}