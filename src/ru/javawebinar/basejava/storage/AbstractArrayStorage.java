package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage capacity overflow", resume.getUuid());
        }
        insertElement(index, resume);
        size++;
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage[index];
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void removeResume(int index, String uuid) {
        shiftLeft(index);
        size--;
        storage[size] = null;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void shiftLeft(int index);
}