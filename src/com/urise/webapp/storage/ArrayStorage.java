package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == storage.length - 1) {
            System.out.println("Storage capacity overflow");
            return;
        }
        if (getIndexOfUuid(resume.getUuid()) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Resume with uuid " + resume.getUuid() + " is already in list");
        }
    }

    public Resume get(String uuid) {
        int i;
        if ((i = getIndexOfUuid(uuid)) != -1) {
            return storage[i];
        } else {
            System.out.println("Resume with uuid " + uuid + " not found");
        }
        return null;
    }

    public void delete(String uuid) {
        int i;
        if ((i = getIndexOfUuid(uuid)) != -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with uuid " + uuid + " not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int i;
        if ((i = getIndexOfUuid(resume.getUuid())) != -1) {
            storage[i] = resume;
        } else {
            System.out.println("Resume with uuid " + resume.getUuid() + " not found");
        }
    }

    private int getIndexOfUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}