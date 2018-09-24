package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute((conn) -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from resume r where uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact r where resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    makeContact(r, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from section r where resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    makeSection(r, rs);
                }
            }
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

            deleteContacts(resume, conn);
            deleteSections(resume, conn);
            insertContacts(resume, conn);
            insertSections(resume, conn);
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from resume r ORDER BY r.full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resume_uuid = rs.getString("uuid");
                    Resume r = new Resume(resume_uuid, rs.getString("full_name"));
                    resumes.put(resume_uuid, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resume_uuid = rs.getString("resume_uuid");
                    makeContact(resumes.get(resume_uuid), rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resume_uuid = rs.getString("resume_uuid");
                    makeSection(resumes.get(resume_uuid), rs);
                }
            }
            return new ArrayList<>(resumes.values());
        });
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
            r.addSection(st, JsonParser.read(s_value, Section.class));
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
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

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (section_value, section_type, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> pair : resume.getSections().entrySet()) {
                Section s_value = pair.getValue();
                String s_type = pair.getKey().toString();
                String resume_uuid = resume.getUuid();
                ps.setString(1, JsonParser.write(s_value, Section.class));
                ps.setString(2, s_type);
                ps.setString(3, resume_uuid);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Resume resume, Connection conn) throws SQLException {
        deleteQuery("DELETE FROM section s WHERE s.resume_uuid=?", resume, conn);
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        deleteQuery("DELETE FROM contact c WHERE c.resume_uuid=?", resume, conn);
    }

    private void deleteQuery(String query, Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
        ;
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