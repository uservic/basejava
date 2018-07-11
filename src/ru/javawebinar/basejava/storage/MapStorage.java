package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void update(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            storage.put(resume.getUuid(), resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            storage.put(resume.getUuid(), resume);
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        if (storage.containsKey(uuid)) {
            return storage.get(uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (storage.remove(uuid) == null) {
            throw new NotExistStorageException(uuid);
        } else {
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected Resume getResume(int index) {
        return null;
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void removeResume(int index) {

    }
}