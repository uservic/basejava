package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {

    private SerializationStrategy strategy;

    public PathStorage(String dir) {
        super(dir);
    }

    public PathStorage(String dir, SerializationStrategy strategy) {
        super(dir);
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