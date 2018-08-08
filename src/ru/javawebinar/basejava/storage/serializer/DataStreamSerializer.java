package ru.javawebinar.basejava.storage.serializer;

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
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().toString());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType key = entry.getKey();
                Section value = entry.getValue();
                dos.writeUTF(key.toString());

                if (key == SectionType.OBJECTIVE || key == SectionType.PERSONAL) {
                    dos.writeUTF(value.toString());
                } else if (key == SectionType.ACHIEVEMENT || key == SectionType.QUALIFICATIONS) {
                    List<String> listItems = ((ListSection) value).getItems();
                    dos.writeInt(listItems.size());
                    for (String item : listItems) {
                        dos.writeUTF(item);
                    }
                } else if (key == SectionType.EXPERIENCE || key == SectionType.EDUCATION) {
                    List<Organization> organizations = ((OrganizationtSection) value).getOrganizations();
                    dos.writeInt(organizations.size());
                    for (Organization org : organizations) {
                        dos.writeUTF(org.getOrgName());
                        dos.writeUTF(org.getOrgUrl());

                        List<Organization.Position> positions = org.getPositions();
                        dos.writeInt(positions.size());
                        for (Organization.Position position : positions) {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getPosition());
                            String description = position.getDescription();
                            dos.writeUTF(description == null ? "" : description);
                        }
                    }
                }
            }
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
}