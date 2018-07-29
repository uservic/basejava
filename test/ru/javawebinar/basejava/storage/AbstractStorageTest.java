package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AbstractStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected static final Resume RESUME_ONE = createResume(UUID_1, "Ann One");
    protected static final Resume RESUME_TWO = createResume(UUID_2, "Bob Two");
    protected static final Resume RESUME_THREE = createResume(UUID_3, "Joe Three");
    protected static final Resume RESUME_DUMMY = new Resume("dummy", "dummyName");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_ONE);
        storage.save(RESUME_TWO);
        storage.save(RESUME_THREE);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = Arrays.asList(RESUME_ONE, RESUME_TWO, RESUME_THREE);
        assertEquals(resumes, storage.getAllSorted());
    }

    @Test
    public void get() {
        assertGet(RESUME_ONE);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(RESUME_DUMMY.getUuid());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3, "Joe_Three_Updated");
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_DUMMY);
    }

    @Test
    public void save() {
        storage.save(RESUME_DUMMY);
        assertSize(4);
        assertGet(RESUME_DUMMY);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_ONE);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(RESUME_DUMMY.getUuid());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int expectedSize) {
        assertEquals(expectedSize, storage.size());
    }

    private static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 921 123 45 67").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("AnnOneskype.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("ann@mail.com").toString());

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
                DateUtil.of(2005, Month.SEPTEMBER),
                DateUtil.of(2007, Month.JUNE),
                "student MS",
                null);

        List<Organization> organizations2 = new ArrayList<>();
        organizations2.add(universityData1);
        organizations2.add(universityData2);
        OrganizationtSection organizationtUniSection = new OrganizationtSection(organizations2);
        resume.addSection(SectionType.EDUCATION, organizationtUniSection);

        return resume;
    }
}