package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    protected List<Resume> doListCopy() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doSave(Object resumeKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object resumeKey) {
        return storage.get(((Resume) resumeKey).getUuid());
    }

    @Override
    protected void doUpdate(Object resumeKey, Resume resume) {
        storage.put(((Resume) resumeKey).getUuid(), resume);
    }

    @Override
    protected void doDelete(Object resumeKey) {
        storage.remove(((Resume) resumeKey).getUuid());
    }

    @Override
    protected boolean keyExist(Object resumeKey) {
        return resumeKey != null;
    }

    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }
}