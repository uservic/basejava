package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResumeCreation {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Bob");

        resume.addContact(ContactType.PHONE_NUMBER, new PlainTextSection("+7 921 123 45 67").getContent());
        resume.addContact(ContactType.SKYPE, new PlainTextSection("skype.address").getContent());
        resume.addContact(ContactType.EMAIL, new PlainTextSection("dummy@mail.com").getContent());

        OrgSiteSection orgSiteSection = new OrgSiteSection("Facebook", "www.fb.com");
        Map<String, String> pairOrgSite = new HashMap<>();
        pairOrgSite.put("LinkedIn", "www.lnkd.com");
        orgSiteSection.addContent(pairOrgSite);
        resume.addContact(ContactType.ORG_SITE, orgSiteSection.getContent());

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

        OrganisationData organisationData1 = new OrganisationData(
                "Organisation1",
                LocalDate.of(2018, 7, 1),
                "org1_content");

        organisationData1.addData(LocalDate.now(), "org1_content_additional" );

        OrganisationData organisationData2 = new OrganisationData(
                "Organisation2",
                LocalDate.of(2018, 7, 2),
                "org2_content");

        organisationData2.addData(LocalDate.now(), "org2_content_additional" );

        List<OrganisationData> organisationDataList = new ArrayList<>();
        organisationDataList.add(organisationData1);
        organisationDataList.add(organisationData2);
        resume.addSection(SectionType.EXPERIENCE, new CompoundTextSection(organisationDataList));

        OrganisationData universityData = new OrganisationData(
                "University1",
                LocalDate.now(),
                "uni1_content");
        OrganisationData universityData2 = new OrganisationData(
                "University2",
                LocalDate.now(),
                "uni2_content");
        List<OrganisationData> universityDataList = new ArrayList<>();
        universityDataList.add(universityData);
        universityDataList.add(universityData2);
        resume.addSection(SectionType.EDUCATION, new CompoundTextSection(universityDataList));

        System.out.println();
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + ": " + resume.getContactByType(contactType));
        }
        System.out.println("--------------------------");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + resume.getSectionByType(sectionType).toString());
        }
    }
}