package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CollectionWriter<T> {
    void writeItem(T collection) throws IOException;
}