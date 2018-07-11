package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume RESUME_ONE = new Resume(UUID_1);
    private static final Resume RESUME_TWO = new Resume(UUID_2);
    private static final Resume RESUME_THREE = new Resume(UUID_3);
    private static final Resume RESUME_DUMMY = new Resume("dummy");

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
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
    public void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(3, resumes.length);
        assertEquals(RESUME_ONE, resumes[0]);
        assertEquals(RESUME_TWO, resumes[1]);
        assertEquals(RESUME_THREE, resumes[2]);
    }

    @Test
    public void get() {
        assertGet(RESUME_ONE);
        assertGet(RESUME_TWO);
        assertGet(RESUME_THREE);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(RESUME_DUMMY.getUuid());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3);
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
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

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Error while filling storage");
        }
        storage.save(new Resume());
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