package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        SqlHelper.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.processQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public int size() {
        return SqlHelper.processQuery("SELECT COUNT(*) AS total FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("total");
        });
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.processQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.processQuery("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.processQuery("UPDATE resume r SET full_name=? WHERE r.uuid=?", (ps) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.processQuery("DELETE FROM resume r WHERE r.uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlHelper.processQuery("SELECT * FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return null;
        });
        Collections.sort(resumes);
        return resumes;
    }
}