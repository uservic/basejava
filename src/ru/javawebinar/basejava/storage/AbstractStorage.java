package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (!isUpdated(resume)) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (containsResume(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public Resume get(String uuid) {
        if (containsResume(uuid)) {
            return getResume(uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (containsResume(uuid)) {
            removeResume(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract boolean isUpdated(Resume resume);

    protected abstract boolean containsResume(String uuid);

    protected abstract void saveResume(Resume resume);

    protected abstract Resume getResume(String uuid);

    protected abstract void removeResume(String uuid);
}