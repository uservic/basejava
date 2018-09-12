package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();

    protected static final Resume RESUME_ONE = createResumeOne(UUID_1, "Ann One");
    protected static final Resume RESUME_TWO = createResumeTwo(UUID_2, "Bob Two");
    protected static final Resume RESUME_THREE = createResumeThree(UUID_3, "Joe Three");
    protected static final Resume RESUME_DUMMY = new Resume("dummy", "dummyName");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();

        storage.save(RESUME_THREE);
        storage.save(RESUME_ONE);
        storage.save(RESUME_TWO);
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
        Resume newResume = createUpdatedResumeThree(UUID_3, "Joe_Three_Updated");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_3));
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

    private static Resume createResumeOne(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 921 123 45 67").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("AnnOneskype.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("ann@mail.com").toString());

        Link linkMySite = new Link("LinkedIn", "www.lnkd/AnnOne.com");
        resume.addContact(ContactType.LINKEDIN, linkMySite.getUrl());

        Link linkMyLinkedIn = new Link("HomePage", "www.Annsite.com");
        resume.addContact(ContactType.HOME_PAGE, linkMyLinkedIn.getUrl());

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущая стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика," +
                " креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(
                "С 2013 года: разработка проектов" +
                " \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность." +
                " XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация" +
                " онлайн стажировок и ведение проектов. Более 1000 выпускников.", "Реализация двухфакторной" +
                " аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity," +
                " Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С," +
                        " Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:" +
                        " Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей," +
                        " интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC," +
                        " GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base" +
                        " архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии" +
                        " через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и" +
                        " мониторинга системы по JMX (Jython/ Django)."));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J," +
                " JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy"));

//        Organization organisationData1 = new Organization(
//                "Organisation1",
//                null,
//                new Organization.Position(2009, Month.JANUARY, 2010, Month.JANUARY,
//                        "org1_pos", "org1_posDescr"),
//                new Organization.Position(2010, Month.JANUARY,
//                        "org1_pos2", "org1_posDescr2"));
//
//        Organization organisationData2 = new Organization(
//                "Organisation2",
//                "www.org2.com",
//                new Organization.Position(2012, Month.JULY, "org2_pos", "org2_posDescr"));
//
//        resume.addSection(SectionType.EXPERIENCE, new OrganizationtSection(organisationData1, organisationData2));
//
//        Organization universityData1 = new Organization(
//                "University1",
//                "www.uni1.com",
//                new Organization.Position(2000, Month.SEPTEMBER, 2005, Month.JUNE,
//                        "student", null));
//
//        Organization universityData2 = new Organization(
//                "University2",
//                "www.uni2.com",
//                new Organization.Position(2005, Month.SEPTEMBER, 2007, Month.JUNE,
//                        "student", null));
//
//        resume.addSection(SectionType.EDUCATION, new OrganizationtSection(universityData1, universityData2));

        return resume;
    }

    private static Resume createResumeTwo(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addSection(SectionType.OBJECTIVE, new TextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("achievement content1", "achievement content2"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("qualifications content1", "qualifications content2"));
//
//        Organization organisationData1 = new Organization(
//                "Organisation1",
//                null,
//                new Organization.Position(2009, Month.JANUARY, 2010, Month.JANUARY,
//                        "org1_pos", "org1_posDescr"),
//                new Organization.Position(2010, Month.JANUARY, 2012, Month.JUNE,
//                        "org1_pos2", "org1_posDescr2"));
//
//        Organization organisationData2 = new Organization(
//                "Organisation2",
//                "www.org2.com",
//                new Organization.Position(2012, Month.JULY, "org2_pos", "org2_posDescr"));
//
//        resume.addSection(SectionType.EXPERIENCE, new OrganizationtSection(organisationData1, organisationData2));
//
//        Organization universityData1 = new Organization(
//                "University1",
//                "www.uni1.com",
//                new Organization.Position(2000, Month.SEPTEMBER, 2005, Month.JUNE,
//                        "student", null));
//
//        Organization universityData2 = new Organization(
//                "University2",
//                "www.uni2.com",
//                new Organization.Position(2005, Month.SEPTEMBER, 2007, Month.JUNE,
//                        "student", null));
//
//        resume.addSection(SectionType.EDUCATION, new OrganizationtSection(universityData1, universityData2));

        return resume;
    }

    private static Resume createResumeThree(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 905 987 65 43").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("JoeThreeSkype.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("joe@mail.com").toString());

        Link linkMySite = new Link("HomePage", "www.mysite.com");
        resume.addContact(ContactType.HOME_PAGE, linkMySite.getUrl());

        Link linkMyLinkedIn = new Link("LinkedIn", "www.lnkd/JoeThree.com");
        resume.addContact(ContactType.LINKEDIN, linkMyLinkedIn.getUrl());

        resume.addSection(SectionType.OBJECTIVE, new TextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("achievement content1", "achievement content2"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("qualifications content1", "qualifications content2"));

        return resume;
    }

    private static Resume createUpdatedResumeThree(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE_NUMBER, new TextSection("+7 111 11 11 11").toString());
        resume.addContact(ContactType.SKYPE, new TextSection("JoeThreeSkypeUpd.address").toString());
        resume.addContact(ContactType.EMAIL, new TextSection("joe_Upd@mail.com").toString());

        Link linkMySite = new Link("HomePage", "www.mysite_updated.com");
        resume.addContact(ContactType.HOME_PAGE, linkMySite.getUrl());

        Link linkMyLinkedIn = new Link("LinkedIn", "www.lnkd/JoeThree.com");
        resume.addContact(ContactType.LINKEDIN, linkMyLinkedIn.getUrl());

        resume.addSection(SectionType.OBJECTIVE, new TextSection("position content"));
        resume.addSection(SectionType.PERSONAL, new TextSection("personal content"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("achievement_Upd content1", "achievement_Upd content2"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("qualifications_Upd content1", "qualifications_Upd content2"));

        return resume;
    }
}