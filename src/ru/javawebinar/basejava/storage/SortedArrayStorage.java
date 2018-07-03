package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void writeElement(Resume resume) {
        int index = getIndex(resume.getUuid());
        int indexForInsert = (index + 1) * (-1);
        for (int i = size; i >= indexForInsert + 1; i--) {
            storage[i] = storage[i - 1];
        }
        storage[indexForInsert] = resume;
    }

    @Override
    protected void deleteElement(int index) {
        for (int i = index + 1; i < size; i++) {
            storage[i - 1] = storage[i];
        }
        size--;
        storage[size] = null;
    }
}