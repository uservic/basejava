package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        sqlHelper.processTransaction("INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                "INSERT INTO contact (value, type, resume_uuid) VALUES (?, ?, ?)",
                resume);

//        sqlHelper.transactionalExecute((conn) -> {
//            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//                ps.setString(1, resume.getUuid());
//                ps.setString(2, resume.getFullName());
//                ps.execute();
//            }
//
//            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, type, resume_uuid)" +
//                    " VALUES (?, ?, ?)")) {
//                for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
//                    ps.setString(1, pair.getValue());
//                    ps.setString(2, pair.getKey().toString());
//                    ps.setString(3, resume.getUuid());
//                    ps.addBatch();
//                }
//                ps.executeBatch();
//            }
//            return null;
//        });
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
                        String type;
                        String value;
                        if ((type = rs.getString("type")) != null
                                && (value = rs.getString("value")) != null) {
                            ContactType ct = ContactType.valueOf(type);
                            r.addContact(ct, value);
                        }
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume resume) {
//        sqlHelper.processTransaction("UPDATE resume SET full_name=? WHERE uuid=?",
//                "UPDATE contact c SET value=? WHERE c.type=? AND c.resume_uuid=?",
//                resume);

        sqlHelper.processTransaction("UPDATE resume SET full_name=? WHERE uuid=?",
               " INSERT INTO contact AS c (value, type, resume_uuid) VALUES (?,?,?)" +
                         " ON CONFLICT(resume_uuid, type)" +
                         " DO UPDATE " +
                         " SET value=? " +
                         " WHERE c.type=? AND c.resume_uuid=?",
                         resume);

//        sqlHelper.transactionalExecute((conn) -> {
//            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
//                ps.setString(1, resume.getFullName());
//                ps.setString(2, resume.getUuid());
//                if (ps.executeUpdate() == 0) {
//                    throw new NotExistStorageException(resume.getUuid());
//                }
//            }
//
//            try (PreparedStatement ps = conn.prepareStatement(
//                    " INSERT INTO contact AS c (value, type, resume_uuid) VALUES (?,?,?)" +
//                            " ON CONFLICT(resume_uuid, type)" +
//                            " DO UPDATE " +
//                            " SET value=? " +
//                            " WHERE c.type=? AND c.resume_uuid=?")) {
//                for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
//                    ps.setString(1, pair.getValue());
//                    ps.setString(2, pair.getKey().toString());
//                    ps.setString(3, resume.getUuid());
//
//                    ps.setString(4, pair.getValue());
//                    ps.setString(5, pair.getKey().toString());
//                    ps.setString(6, resume.getUuid());
//
//                    ps.addBatch();
//                }
//                ps.executeBatch();
//            }
//            return null;
//        });
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
        return sqlHelper.processQuery("SELECT * FROM resume ORDER BY full_name, uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
//                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                resumes.add(get(rs.getString("uuid")));
            }
            return resumes;
        });
    }
}