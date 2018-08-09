package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            contacts.forEach((k, v) -> {
                writeStringData(dos, k.toString());
                writeStringData(dos, v);
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());

            sections.forEach((k, v) -> {
                writeStringData(dos, k.toString());
                switch (k) {
                    case OBJECTIVE:
                        writeStringData(dos, v.toString());
                        break;
                    case PERSONAL:
                        writeStringData(dos, v.toString());
                        break;
                    case ACHIEVEMENT:
                        writeItemsList(dos, v);
                        break;
                    case QUALIFICATIONS:
                        writeItemsList(dos, v);
                        break;
                    case EXPERIENCE:
                        writeOrgList(dos, v);
                        break;
                    case EDUCATION:
                        writeOrgList(dos, v);
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

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                ContactType ct = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(ct, value);
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());

                if (st == SectionType.OBJECTIVE || st == SectionType.PERSONAL) {
                    String value = dis.readUTF();
                    resume.addSection(st, new TextSection(value));
                } else if (st == SectionType.ACHIEVEMENT || st == SectionType.QUALIFICATIONS) {
                    List<String> items = new ArrayList<>();
                    int itemsListSize = dis.readInt();
                    for (int j = 0; j < itemsListSize; j++) {
                        items.add(dis.readUTF());
                    }
                    resume.addSection(st, new ListSection(items));
                } else if (st == SectionType.EXPERIENCE || st == SectionType.EDUCATION) {
                    List<Organization> organizations = new ArrayList<>();
                    int orgListSize = dis.readInt();
                    for (int j = 0; j < orgListSize; j++) {
                        String orgName = dis.readUTF();
                        String orgUrl = dis.readUTF();
                        if (orgUrl.equals("")) {
                            orgUrl = null;
                        }

                        List<Organization.Position> positions = new ArrayList<>();
                        int positionsListSize = dis.readInt();
                        for (int k = 0; k < positionsListSize; k++) {
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            String position = dis.readUTF();
                            String description = dis.readUTF();
                            if (description.equals("")) {
                                description = null;
                            }
                            positions.add(new Organization.Position(startDate, endDate, position, description));
                        }
                        organizations.add(new Organization(new Link(orgName, orgUrl), positions));
                    }
                    resume.addSection(st, new OrganizationtSection(organizations));
                }
            }
            return resume;
        }
    }

    private void writeItemsList(DataOutputStream dos, Section section) {
        List<String> listItems = ((ListSection) section).getItems();
        try {
            dos.writeInt(listItems.size());
            listItems.forEach(item -> writeStringData(dos, item));
        } catch (IOException e) {
            throw new StorageException("Data writing error", e);
        }
    }

    private void writeOrgList(DataOutputStream dos, Section section) {
        List<Organization> organizations = ((OrganizationtSection) section).getOrganizations();
        writeIntData(dos, organizations.size());
        organizations.forEach(org -> {
            writeStringData(dos, org.getOrgName());
            String orgUrl = org.getOrgUrl();
            writeStringData(dos, (orgUrl == null ? "" : orgUrl));

            List<Organization.Position> positions = org.getPositions();
            writeIntData(dos, positions.size());
            positions.forEach(pos -> {
                writeStringData(dos, pos.getStartDate().toString());
                writeStringData(dos, pos.getEndDate().toString());
                writeStringData(dos, pos.getPosition());
                String description = pos.getDescription();
                writeStringData(dos, (description == null ? "" : description));
            });
        });
    }


    private void writeStringData(DataOutputStream dos, String data) {
        try {
            dos.writeUTF(data);
        } catch (IOException e) {
            throw new StorageException("Data writing error", e);
        }
    }

    private void writeIntData(DataOutputStream dos, int data) {
        try {
            dos.writeInt(data);
        } catch (IOException e) {
            throw new StorageException("Data writing error", e);
        }
    }
}