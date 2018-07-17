package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

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
    protected Resume getResume(Object index, String uuid) {
        return storage[(int) index];
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void removeResume(Object index, String uuid) {
        shiftLeft((int) index);
        size--;
        storage[size] = null;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void shiftLeft(int index);
}