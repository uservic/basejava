package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = doAllCopy();
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void save(Resume resume) {
        SK key = getNotExistedSearchKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        SK key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    @Override
    public void update(Resume resume) {
        SK key = getExistedSearchKey(resume.getUuid());
        doUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        SK key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    private SK getExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (!keyExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (keyExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> doAllCopy();

    protected abstract boolean keyExist(SK key);

    protected abstract void doSave(SK key, Resume resume);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract void doUpdate(SK key, Resume resume);

    protected abstract SK getKey(String uuid);
}