package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <R> R processQuery(String query, ResultSupplier<PreparedStatement, R> getter) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
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

    public void processTransaction(String queryOne, String queryTwo, Resume resume) {
        transactionalExecute((conn) -> {
            String resume_uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement(queryOne)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume_uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume_uuid);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(queryTwo)) {
                boolean addParams = checkAdditionalParams(queryTwo);

                for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
                    String value = pair.getValue();
                    String type = pair.getKey().toString();

                    ps.setString(1, value);
                    ps.setString(2, type);
                    ps.setString(3, resume_uuid);

                    if (addParams) {
                        ps.setString(4, value);
                        ps.setString(5, type);
                        ps.setString(6, resume_uuid);
                    }

                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    private boolean checkAdditionalParams(String query) {
        return query.chars().filter((ch) -> ch == '?').count() > 3;
    }
}