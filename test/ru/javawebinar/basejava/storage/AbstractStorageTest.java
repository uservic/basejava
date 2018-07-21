package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AbstractStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final String NAME_1 = "Ann One";
    private static final String NAME_2 = "Bob Two";
    private static final String NAME_3 = "Joe Three";

    protected static final Resume RESUME_ONE = new Resume(UUID_1, NAME_1);
    protected static final Resume RESUME_TWO = new Resume(UUID_2, NAME_2);
    protected static final Resume RESUME_THREE = new Resume(UUID_3, NAME_3);
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
        List<Resume> resumes = new ArrayList<>();
        resumes.add(RESUME_ONE);
        resumes.add(RESUME_TWO);
        resumes.add(RESUME_THREE);
        assertEquals(resumes, storage.getAllSorted() );
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
        Resume newResume = new Resume(UUID_3, NAME_3);
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
}