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
    protected void saveResume(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage capacity overflow", resume.getUuid());
        }
        insertElement(getIndex(resume.getUuid()), resume);
        size++;
    }

    @Override
    protected boolean isUpdated(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage[getIndex(uuid)];
    }

    @Override
    protected boolean containsResume(String uuid) {
        return getIndex(uuid) >= 0;
    }

    @Override
    protected void removeResume(String uuid) {
        shiftLeft(getIndex(uuid));
        size--;
        storage[size] = null;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void shiftLeft(int index);
}