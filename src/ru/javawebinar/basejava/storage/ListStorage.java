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
    protected void saveResume(Object key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object key, String uuid) {
        return storage.get((int) key);
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        storage.add((int) key, resume);
    }

    @Override
    protected void removeResume(Object key, String uuid) {
        storage.remove((int) key);
    }

    @Override
    protected int getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}