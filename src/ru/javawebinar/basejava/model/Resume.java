package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");

        contacts.put(ContactType.PHONE_NUMBER, new PlainTextSection("+7 921 123 45 67").getContent());
        contacts.put(ContactType.SKYPE, new PlainTextSection("skype.address").getContent());
        contacts.put(ContactType.EMAIL, new PlainTextSection("dummy@mail.com").getContent());

        OrgSiteSection orgSiteSection = new OrgSiteSection("Facebook", "www.fb.com");
        Map<String, String> pairOrgSite = new HashMap<>();
        pairOrgSite.put("LinkedIn", "www.lnkd.com");
        orgSiteSection.addContent(pairOrgSite);
        contacts.put(ContactType.ORG_SITE, orgSiteSection.getContent());

        sections.put(SectionType.OBJECTIVE, new PlainTextSection("position content"));
        sections.put(SectionType.PERSONAL, new PlainTextSection("personal content"));

        Section achievemntSection = new ListTextSection();
        achievemntSection.addContent("achievement content1");
        achievemntSection.addContent("achievement content2");
        sections.put(SectionType.ACHIEVEMENT, achievemntSection);

        Section qualificationsSection = new ListTextSection();
        qualificationsSection.addContent("qualifications content1");
        qualificationsSection.addContent("qualifications content2");
        sections.put(SectionType.QUALIFICATIONS, qualificationsSection);

        OrganisationData organisationData1 = new OrganisationData(
                                                            "Organisation1",
                                                                  LocalDate.of(2018, 7, 1),
                                                          "org1_content");

        organisationData1.addData(LocalDate.now(), "org1_content_additional" );

        OrganisationData organisationData2 = new OrganisationData(
                                                            "Organisation2",
                                                                  LocalDate.of(2018, 7, 2),
                                                          "org2_content");

        organisationData1.addData(LocalDate.now(), "org2_content_additional" );

        List<OrganisationData> organisationDataList = new ArrayList<>();
        organisationDataList.add(organisationData1);
        organisationDataList.add(organisationData2);
        sections.put(SectionType.EXPERIENCE, new CompoundTextSection(organisationDataList));

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
        sections.put(SectionType.EDUCATION, new CompoundTextSection(universityDataList));
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactByType(ContactType ct) {
        return contacts.get(ct);
    }

    public Section getSectionByType(SectionType st) {
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