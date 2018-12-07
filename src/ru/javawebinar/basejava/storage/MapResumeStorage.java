package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected List<Resume> doAllCopy() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doSave(Resume resumeKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume resumeKey) {
        return resumeKey;
    }

    @Override
    protected void doUpdate(Resume resumeKey, Resume resume) {
        storage.put(resumeKey.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume resumeKey) {
        storage.remove(resumeKey.getUuid());
    }

    @Override
    protected boolean keyExist(Resume resumeKey) {
        return resumeKey != null;
    }

    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }
}