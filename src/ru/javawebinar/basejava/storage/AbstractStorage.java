package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = checkIndex(uuid);
        return getResume(index, uuid);
    }

    @Override
    public void update(Resume resume) {
        int index = checkIndex(resume.getUuid());
        updateResume(index, resume);
    }

    @Override
    public void delete(String uuid) {
        int index = checkIndex(uuid);
        removeResume(index, uuid);
    }

    private int checkIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void saveResume(int index, Resume resume);

    protected abstract Resume getResume(int index, String uuid);

    protected abstract void removeResume(int index, String uuid);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract int getIndex(String uuid);
}