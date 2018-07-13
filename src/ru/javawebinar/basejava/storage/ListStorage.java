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
    protected void saveResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.add(index, resume);
    }

    @Override
    protected void removeResume(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}