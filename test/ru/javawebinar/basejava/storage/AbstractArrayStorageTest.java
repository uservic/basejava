package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private Resume resumeOne = new Resume(UUID_1);
    private Resume resumeTwo = new Resume(UUID_2);
    private Resume resumeThree = new Resume(UUID_3);
    private Resume resumeDummy = new Resume("dummy");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resumeOne);
        storage.save(resumeTwo);
        storage.save(resumeThree);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{resumeOne, resumeTwo, resumeThree};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void get() {
        Assert.assertEquals(resumeTwo, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void update() {
        Resume oldResume = storage.get(UUID_3);
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        Assert.assertNotSame(storage.get(UUID_3), oldResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resumeDummy);
    }

    @Test
    public void save() {
        Resume resume = resumeDummy;
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(resumeOne);
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - 3; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail("Error while filling storage");
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete("dummy");
    }
}