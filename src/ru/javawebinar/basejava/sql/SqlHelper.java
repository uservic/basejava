package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <R> R processQuery(String query, ResultSupplier<PreparedStatement, R> getter) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            return getter.get(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface ResultSupplier<T, R> {
        R get(T ps) throws SQLException;
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <R> R processShortQuery(String query, Resume resume, Connection conn, ResultSupplier<PreparedStatement, R> getter) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return getter.get(ps);
        }
    }

    public void processLongQuery(String query, Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
                String value = pair.getValue();
                String type = pair.getKey().toString();
                String resume_uuid = resume.getUuid();
                ps.setString(1, value);
                ps.setString(2, type);
                ps.setString(3, resume_uuid);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}