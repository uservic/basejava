package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int size = 0;

    @Override
    public void clear() {
        clearStorage();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    @Override
    public Resume[] getAll() {
        return null;
    }

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(int index);

    protected abstract void clearStorage();

    protected abstract void removeResume(int index);
}
