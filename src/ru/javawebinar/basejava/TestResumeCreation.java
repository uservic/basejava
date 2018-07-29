package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 921 123 45 67").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("skype.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("dummy@mail.com").toString());

        Link linkMySite = new Link("HomePage", "www.mysite.com");
        resume.addContact(ContactType.HOME_PAGE, linkMySite.getUrl());

        Link linkMyLinkedIn = new Link("LinkedIn", "www.lnkd/AnnOne.com");
        resume.addContact(ContactType.HOME_PAGE, linkMyLinkedIn.getUrl());

        resume.addSection(SectionType.OBJECTIVE, new TextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal content"));

        List<String> achievementList = new ArrayList<>();
        achievementList.add("achievement content1");
        achievementList.add("achievement content2");
        ListSection achievemntSection = new ListSection(achievementList);
        resume.addSection(SectionType.ACHIEVEMENT, achievemntSection);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("qualifications content1");
        qualificationsList.add("qualifications content2");
        ListSection qualificationsSection = new ListSection(qualificationsList);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        Organization organisationData1 = new Organization(
                "Organisation1",
                "www.org1.com",
                DateUtil.of(2008, Month.JANUARY),
                DateUtil.of(2009, Month.NOVEMBER),
                "org1_pos",
                "org1_posDescr");
        organisationData1.addOrganizationContent(
                DateUtil.of(2010, Month.JANUARY),
                DateUtil.of(2012, Month.JUNE),
                "org1_pos2",
                "org1_posDescr2");

        Organization organisationData2 = new Organization(
                "Organisation2",
                "www.org2.com",
                DateUtil.of(2012, Month.AUGUST),
                DateUtil.of(2013, Month.DECEMBER),
                "org2_pos",
                "org2_posDescr");

        List<Organization> organizations1 = new ArrayList<>();
        organizations1.add(organisationData1);
        organizations1.add(organisationData2);
        OrganizationtSection organizationtSection = new OrganizationtSection(organizations1);

        resume.addSection(SectionType.EXPERIENCE, organizationtSection);

        Organization universityData1 = new Organization(
                "University1",
                "www.uni1.com",
                DateUtil.of(2000, Month.SEPTEMBER),
                DateUtil.of(2005, Month.JUNE),
                "student",
                null);

        Organization universityData2 = new Organization(
                "University2",
                "www.uni2.com",
                DateUtil.of(2002, Month.SEPTEMBER),
                DateUtil.of(2007, Month.JUNE),
                "student MS",
                null);

        List<Organization> organizations2 = new ArrayList<>();
        organizations2.add(universityData1);
        organizations2.add(universityData2);
        OrganizationtSection organizationtUniSection = new OrganizationtSection(organizations2);
        resume.addSection(SectionType.EDUCATION, organizationtUniSection);

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