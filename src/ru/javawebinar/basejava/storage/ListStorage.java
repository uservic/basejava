package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

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
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected void saveResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isUpdated(Resume resume) {
        if (storage.contains(resume)) {
            storage.add(getIndex(resume.getUuid()), resume);
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage.get(getIndex(uuid));
    }

    @Override
    protected boolean containsResume(String uuid) {
        return getIndex(uuid) >= 0;
    }

    @Override
    protected void removeResume(String uuid) {
        storage.remove(getIndex(uuid));
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}