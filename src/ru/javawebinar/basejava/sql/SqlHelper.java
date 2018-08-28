package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static ConnectionFactory connectionFactory;

    public static <R> R processQuery(String query, ResultSupplier<PreparedStatement, R> getter) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return getter.get(ps);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException("uuid already exists");
            } else {
                throw new StorageException(e);
            }
        }
    }

    @FunctionalInterface
    public interface ResultSupplier<T, R> {
        R get(T ps) throws SQLException;
    }
}