package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException sqle) {
        if (sqle instanceof PSQLException) {
            if ("23505".equals(sqle.getSQLState())) {
                return new ExistStorageException("Resume already exists");
            }
        }
        return new StorageException(sqle);
    }
}
