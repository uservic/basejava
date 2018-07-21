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
    protected Resume[] doAllCopy() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected void doSave(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean keyExist(Object key) {
        return key != null;
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}