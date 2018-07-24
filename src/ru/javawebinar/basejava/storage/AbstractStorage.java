package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = doAllCopy();
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getNotExistedSearchKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getExistedSearchKey(resume.getUuid());
        doUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    private SK getExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (!keyExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (keyExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
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