package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public List<Resume> getAllSorted() {
        Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        Arrays.sort(resumes);
        return Arrays.asList(resumes);
    }

    @Override
    protected void doSave(Object index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage capacity overflow", resume.getUuid());
        }
        insertElement((int) index, resume);
        size++;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        shiftLeft((int) index);
        size--;
        storage[size] = null;
    }

    @Override
    protected boolean keyExist(Object key) {
        return (int) key >= 0;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void shiftLeft(int index);

    @Override
    protected abstract Integer getKey(String uuid);

    public Resume[] getStorage() {
        return storage;
    }
}