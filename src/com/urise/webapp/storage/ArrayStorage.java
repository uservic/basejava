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
        if (size == 9999) {
            System.out.println("Storage capacity overflow");
            return;
        }
        if (!contains(resume.getUuid())) {
            int indexForNewElement = size;
            storage[indexForNewElement] = resume;
            size++;
        } else {
            System.out.println("com.urise.webapp.model.Resume is already in list");
        }
    }

    public Resume get(String uuid) {
        if (contains(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else {
            System.out.println("com.urise.webapp.model.Resume not found");
        }
        return null;
    }

    public void delete(String uuid) {
        if (contains(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    break;
                }
            }
        } else {
            System.out.println("com.urise.webapp.model.Resume not found");
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
        if (contains(resume.getUuid())) {
           delete(resume.getUuid());
           save(resume);
        } else {
            System.out.println("com.urise.webapp.model.Resume not found");
        }
    }

    private boolean contains(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}