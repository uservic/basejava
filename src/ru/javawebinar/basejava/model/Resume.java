package ru.javawebinar.basejava.model;

import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int compareTo(Resume o) {
        int result = this.fullName.compareTo(o.getFullName());
        if (result != 0) {
            return result;
        } else {
            return this.uuid.compareTo(o.getUuid());
        }
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return fullName;
    }
}