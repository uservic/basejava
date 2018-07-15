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
    protected void saveResume(Integer key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Integer key, String uuid) {
        return storage.get(key);
    }

    @Override
    protected void updateResume(Integer key, Resume resume) {
        storage.add(key, resume);
    }

    @Override
    protected void removeResume(Integer key, String uuid) {
        storage.remove(key.intValue());
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