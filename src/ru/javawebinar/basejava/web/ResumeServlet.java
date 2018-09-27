package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r;
        boolean isNew = false;

        if (uuid == null || uuid.length() == 0) {
            isNew = true;
        }

        if (isNew) {
            r = new Resume("Новое Резюме");
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!HtmlUtil.isEmpty(value)) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
           String value = request.getParameter(type.name());

            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length <= 1) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.setSection(type, new TextSection(value.trim()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new ListSection(value.trim().split("\r\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                            List<Organization> organizationList = new ArrayList<>();
                            String orgListSize = request.getParameter(type + "orgListSize");

                            for (int i = 0; i < Integer.valueOf(orgListSize); i++) {
                                String orgName = request.getParameter(type + "orgName" + i);
                                if (!HtmlUtil.isEmpty(orgName)) {
                                    String orgUrl = request.getParameter(type + "orgUrl" + i);
                                    List<Organization.Position> positions = new ArrayList<>();

                                    String posListSize = request.getParameter(type + "posListSize" + i);
                                    for (int j = 0; j < Integer.valueOf(posListSize); j++) {
                                        String orgPosName = request.getParameter(type.toString() + i + "posName" + j);
                                        if (!HtmlUtil.isEmpty(orgPosName)) {
                                            String startDate = request.getParameter(type.toString() + i + "startDate" + j);
                                            String endDate = request.getParameter(type.toString() + i + "endDate" + j);
                                            String posDescription = request.getParameter(type.toString() + i + "posDescr" + j);
                                            positions.add(new Organization.Position(DateUtil.toShortDate(startDate),
                                                    endDate.equals("Сейчас") ? DateUtil.NOW : DateUtil.toShortDate(endDate),
                                                    orgPosName,
                                                    posDescription));
                                        }
                                    }
                                    organizationList.add(new Organization(new Link(orgName, orgUrl), positions));
                                }
                            }
                            r.setSection(type, new OrganizationSection(organizationList));
                        break;
                }
            }
        }

        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "create":
                r = Resume.makeEmptyResume();
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> emptyFirstOrgs = new ArrayList<>();
                            emptyFirstOrgs.add(Organization.EMPTY);
                            OrganizationSection organizationSection = (OrganizationSection) r.getSection(type);
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getOrganizations()) {
                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Position.EMPTY);
                                    emptyFirstPositions.addAll(org.getPositions());
                                    emptyFirstOrgs.add(new Organization(new Link(org.getOrgName(), org.getOrgUrl()), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrgs);
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}