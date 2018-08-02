package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {

    private SerializationStrategy strategy;

    public FileStorage(File directory) {
        super(directory);
    }

    public FileStorage(File directory, SerializationStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    public void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        strategy.doWrite(os, resume);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}