package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }
        this.sqlHelper = new SqlHelper(() ->
                DriverManager.getConnection(dbUrl, dbUser, dbPassword));
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
            processQuery("INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                    resume, conn, PreparedStatement::execute);
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.processQuery("" +
                        " SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        " ON r.uuid=c.resume_uuid" +
                        " LEFT JOIN section s " +
                        " ON r.uuid=s.resume_uuid" +
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
                        makeSection(r, rs);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute((conn) -> {
            processQuery("UPDATE resume SET full_name=? WHERE uuid=?", resume, conn, (ps) -> {
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            });

            deleteQuery("DELETE FROM contact c WHERE c.resume_uuid=?", resume);
            deleteQuery("DELETE FROM section s WHERE s.resume_uuid=?", resume);
            insertContact(resume, conn);
            insertSection(resume, conn);
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

    //    @Override
    public List<Resume> _getAllSorted() {
        return sqlHelper.processQuery("" +
                " SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                " ON r.uuid=c.resume_uuid" +
                " LEFT JOIN section s " +
                " ON r.uuid=s.resume_uuid" +
                " ORDER BY full_name, uuid", (ps) -> {

            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();

            while (rs.next()) {
                String resume_uuid = rs.getString("uuid");
                Resume r = map.get(resume_uuid);
                if (r == null) {
                    r = new Resume(resume_uuid, rs.getString("full_name"));
                    map.put(resume_uuid, r);
                }
                makeContact(r, rs);
                makeSection(r, rs);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = new LinkedHashMap<>();
        sqlHelper.processQuery("" +
                " SELECT * from resume r " +
                " ORDER BY r.full_name, uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resume_uuid = rs.getString("uuid");
                Resume r = new Resume(resume_uuid, rs.getString("full_name"));
                map.put(resume_uuid, r);
            }
            return null;
        });

        sqlHelper.processQuery("SELECT * from contact", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resume_uuid = rs.getString("resume_uuid");
                makeContact(map.get(resume_uuid), rs);
            }
            return null;
        });

        sqlHelper.processQuery("SELECT * from section", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resume_uuid = rs.getString("resume_uuid");
                makeSection(map.get(resume_uuid), rs);
            }
            return null;
        });
        return new ArrayList<>(map.values());
    }


    private void makeContact(Resume r, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null) {
            ContactType ct = ContactType.valueOf(type);
            r.addContact(ct, value);
        }
    }

    private void makeSection(Resume r, ResultSet rs) throws SQLException {
        String s_type = rs.getString("section_type");
        String s_value = rs.getString("section_value");
        if (s_value != null) {
            SectionType st = SectionType.valueOf(s_type);
            switch (st) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(st, new TextSection(s_value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    s_value = s_value.replace("\\n", "\n");
                    List<String> items = Arrays.asList(s_value.split("\n"));
                    r.addSection(st, new ListSection(items));
                    break;
            }
        }
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (value, type, resume_uuid) VALUES (?, ?, ?)")) {
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

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (section_value, section_type, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> pair : resume.getSections().entrySet()) {
                String value = pair.getValue().toString();
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

    private void deleteQuery(String query, Resume resume) {
        sqlHelper.processQuery(query, (ps) -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void processQuery(String query, Resume resume, Connection conn,
                              resultConsumer getter) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            getter.consume(ps);
        }
    }

    @FunctionalInterface
    private interface resultConsumer {
        void consume(PreparedStatement ps) throws SQLException;
    }
}