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
    protected void saveResume(Object index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage capacity overflow", resume.getUuid());
        }
        insertElement((int) index, resume);
        size++;
    }

    @Override
    protected Resume getResume(Object key, String uuid) {
        return storage[(int) key];
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        storage[(int) key] = resume;
    }

    @Override
    protected void removeResume(Object key, String uuid) {
        shiftLeft((int) key);
        size--;
        storage[size] = null;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void shiftLeft(int index);
}