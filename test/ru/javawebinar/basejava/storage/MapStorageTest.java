package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
        Resume[] resumes = new Resume[]{RESUME_ONE, RESUME_TWO, RESUME_THREE};
        Resume[] testResumes = storage.getAll();
        Arrays.sort(testResumes);
        assertArrayEquals(resumes, testResumes);
    }

    @Test
    public void saveStorageException() {

    }
}