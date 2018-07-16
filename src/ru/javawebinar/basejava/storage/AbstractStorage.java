package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int key = getNotExistedKey(resume.getUuid());
        saveResume(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        int key = getExistedKey(uuid);
        return getResume(key, uuid);
    }

    @Override
    public void update(Resume resume) {
        int key = getExistedKey(resume.getUuid());
        updateResume(key, resume);
    }

    @Override
    public void delete(String uuid) {
        int key = getExistedKey(uuid);
        removeResume(key, uuid);
    }

    private int getExistedKey(String uuid) {
        int key = getKey(uuid);
        if (key < 0) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private int getNotExistedKey(String uuid) {
        int key = getKey(uuid);
        if (key >= 0) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void saveResume(Object key, Resume resume);

    protected abstract Resume getResume(Object key, String uuid);

    protected abstract void removeResume(Object key, String uuid);

    protected abstract void updateResume(Object key, Resume resume);

    protected abstract int getKey(String uuid);
}