package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void saveResume(Integer index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Integer key, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void updateResume(Integer key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void removeResume(Integer key, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected int getKey(String uuid) {
        return storage.containsKey(uuid) ? 1 : -1;
    }
}