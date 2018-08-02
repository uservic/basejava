package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {

    void doWrite(OutputStream os, Resume resume) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}