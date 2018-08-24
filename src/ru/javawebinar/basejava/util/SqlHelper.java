package ru.javawebinar.basejava.util;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static void processQuery(ConnectionFactory factory, String query, Processor<PreparedStatement> processor) {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            processor.process(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                throw new ExistStorageException("uuid already exists");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public static <R> R getResultFromQuery(ConnectionFactory factory, String query,
                                           ResultSupplier<PreparedStatement, R> getter) {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return getter.get(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Processor<T> {
        void process(T ps) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultSupplier<T, R> {
        R get(T ps) throws SQLException;
    }
}