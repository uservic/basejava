package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(doAllCopy());
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void save(Resume resume) {
        Object key = getNotExistedSearchKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    @Override
    public void update(Resume resume) {
        Object key = getExistedSearchKey(resume.getUuid());
        doUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    private Object getExistedSearchKey(String uuid) {
        Object key = getKey(uuid);
        if (!keyExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object key = getKey(uuid);
        if (keyExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract Resume[] doAllCopy();

    protected abstract boolean keyExist(Object key);

    protected abstract void doSave(Object key, Resume resume);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract Object getKey(String uuid);
}