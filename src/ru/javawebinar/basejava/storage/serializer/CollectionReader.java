package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CollectionReader {
    void readItem() throws IOException;
}