package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

        resume.addContact(ContactType.PHONE_NUMBER, new PlainTextSection("+7 921 123 45 67").getContent());
        resume.addContact(ContactType.SKYPE, new PlainTextSection("skype.address").getContent());
        resume.addContact(ContactType.EMAIL, new PlainTextSection("dummy@mail.com").getContent());

        Link linkFb = new Link("Facebook", "www.fb.com");
        resume.addContact(ContactType.ORG_SITE, linkFb.getLink());

        resume.addSection(SectionType.OBJECTIVE, new PlainTextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new PlainTextSection("personal content"));

        ListTextSection achievemntSection = new ListTextSection();
        achievemntSection.addContent("achievement content1");
        achievemntSection.addContent("achievement content2");
        resume.addSection(SectionType.ACHIEVEMENT, achievemntSection);

        ListTextSection qualificationsSection = new ListTextSection();
        qualificationsSection.addContent("qualifications content1");
        qualificationsSection.addContent("qualifications content2");
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        Link org1_link = new Link("Organisation1", "www.org1.com");
        OrganisationData organisationData1 = new OrganisationData(
                "Organisation1",
                org1_link,
                LocalDate.of(2018, 7, 1),
                "org1_pos",
                "org1_posDescr");

        Link org2_link = new Link("Organisation2", "www.org2.com");
        OrganisationData organisationData2 = new OrganisationData(
                "Organisation2",
                org2_link,
                LocalDate.of(2018, 7, 2),
                "org2_pos",
                "org2_posDescr");


        CompoundTextSection compoundOrgTextSection = new CompoundTextSection();
        compoundOrgTextSection.addContent(organisationData1);
        compoundOrgTextSection.addContent(organisationData2);
        resume.addSection(SectionType.EXPERIENCE, compoundOrgTextSection);

        Link uni1_link = new Link("University1", "www.uni1.com");
        OrganisationData universityData = new OrganisationData(
                "University1",
                uni1_link,
                LocalDate.now(),
                null,
                "uni1_content");

        Link uni2_link = new Link("University2", "www.uni2.com");
        OrganisationData universityData2 = new OrganisationData(
                "University2",
                uni2_link,
                LocalDate.now(),
                null,
                "uni2_content");

        CompoundTextSection compoundUniTextSection = new CompoundTextSection();
        compoundUniTextSection.addContent(universityData);
        compoundUniTextSection.addContent(universityData2);
        resume.addSection(SectionType.EDUCATION, compoundOrgTextSection);

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