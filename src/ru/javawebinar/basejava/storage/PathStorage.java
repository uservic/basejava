package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StreamSerializer strategy;

    protected PathStorage(String dir, StreamSerializer strategy) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        getDirStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getDirStream().count();
    }

    @Override
    public List<Resume> doAllCopy() {
        return getDirStream()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Could not create file " + file, getFileName(file), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path file) {
        Resume resume;
        try {
            resume = strategy.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileName(file), e);
        }
        return resume;
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            strategy.doWrite(new BufferedOutputStream(Files.newOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileName(file), e);
        }
    }

    @Override
    protected boolean keyExist(Path file) {
        return Files.isRegularFile(file);
    }

    @Override
    protected Path getKey(String uuid) {
        return directory.resolve(uuid);
    }

    private Stream<Path> getDirStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error opening directory", e);
        }
    }

    private String getFileName(Path file) {
        return file.getFileName().toString();
    }
}