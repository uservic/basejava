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
    protected void writeElement(int index, Resume resume) {
        int indexForInsert = (index + 1) * (-1);
        System.arraycopy(storage, indexForInsert, storage, indexForInsert + 1, size - indexForInsert);
        storage[indexForInsert] = resume;
    }

    @Override
    protected void shiftLeft(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}