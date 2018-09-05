package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.processQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public int size() {
        return sqlHelper.processQuery("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute((conn) -> {
            sqlHelper.processShortQuery("INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                    resume, conn, PreparedStatement::execute);
            sqlHelper.processLongQuery("INSERT INTO contact (value, type, resume_uuid) VALUES (?, ?, ?)",
                    resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.processQuery("" +
                        " SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        " ON r.uuid=c.resume_uuid" +
                        " WHERE r.uuid =? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        makeContact(r, rs);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute((conn) -> {
            sqlHelper.processShortQuery("UPDATE resume SET full_name=? WHERE uuid=?", resume, conn, (ps) -> {
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                return null;
            });

            sqlHelper.processQuery("DELETE FROM contact c WHERE c.resume_uuid=?", (ps) -> {
                ps.setString(1, resume.getUuid());
                ps.execute();
                return null;
            });
            sqlHelper.processLongQuery("INSERT INTO contact (value, type, resume_uuid) VALUES (?, ?, ?)",
                    resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>processQuery("DELETE FROM resume r WHERE r.uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.processQuery("" +
                " SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                " ON r.uuid=c.resume_uuid" +
                " ORDER BY full_name, uuid", (ps) -> {

            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();

            while (rs.next()) {
                String resume_uuid = rs.getString("uuid");
                Resume r = new Resume(resume_uuid, rs.getString("full_name"));
                do {
                    makeContact(r, rs);
                } while (rs.next() && resume_uuid.equals(rs.getString("uuid")));
                rs.previous();
                resumes.add(r);
            }
            return resumes;
        });
    }

    private void makeContact(Resume r, ResultSet rs) throws SQLException {
        String type;
        String value;
        if ((type = rs.getString("type")) != null
                && (value = rs.getString("value")) != null) {
            ContactType ct = ContactType.valueOf(type);
            r.addContact(ct, value);
        }
    }
}