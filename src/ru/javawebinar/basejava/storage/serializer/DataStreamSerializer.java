package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().toString());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();

            writeCollection(dos, sections.entrySet(), (entry) -> {
                SectionType key = entry.getKey();
                Section value = entry.getValue();
                dos.writeUTF(key.toString());

                switch (key) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) value).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) value).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationtSection) value).getOrganizations(), (org) -> {
                            dos.writeUTF(org.getOrgName());
                            dos.writeUTF(org.getOrgUrl());

                            writeCollection(dos, org.getPositions(), (pos) -> {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getPosition());
                                dos.writeUTF(pos.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readCollection(dis, () -> {
                ContactType ct = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(ct, value);
            });

            readCollection(dis, () -> {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        String value = dis.readUTF();
                        resume.addSection(st, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = new ArrayList<>();
                        readCollection(dis, () -> {
                            items.add(dis.readUTF());
                        });
                        resume.addSection(st, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        readCollection(dis, () -> {
                            String orgName = dis.readUTF();
                            String orgUrl = dis.readUTF();

                            List<Organization.Position> positions = new ArrayList<>();
                            readCollection(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String position = dis.readUTF();
                                String description = dis.readUTF();

                                positions.add(new Organization.Position(startDate, endDate, position, description));
                            });
                            organizations.add(new Organization(new Link(orgName, orgUrl), positions));
                        });
                        resume.addSection(st, new OrganizationtSection(organizations));
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, CollectionWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.writeItem(item);
        }
    }

    private void readCollection(DataInputStream dis, CollectionReader reader) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            reader.readItem();
        }
    }

    @FunctionalInterface
    private interface CollectionWriter<T> {
        void writeItem(T collection) throws IOException;
    }

    @FunctionalInterface
    private interface CollectionReader {
        void readItem() throws IOException;
    }
}