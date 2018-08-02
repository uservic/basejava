package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        dirList(directory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return dirList(directory).size();
    }

    @Override
    protected List<Resume> doAllCopy() {
        List<Resume> resumes = new ArrayList<>();
        for (Path file : dirList(directory)) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Could not create file ", file.toString(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path file) {
        Resume resume;
        try {
            resume = doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.toString(), e);
        }
        return resume;
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", file.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", file.toString(), e);
        }
    }

    @Override
    protected boolean keyExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected Path getKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    private List<Path> dirList(Path directory) {
        try {
            return Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error opening directory", null);
        }
    }
}