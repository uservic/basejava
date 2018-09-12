package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.util.List;
import java.util.Map;

public class HtmlUtil {
    public static String getPage(String table) {
        return String.format("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Resume table</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <hr width=\"1024\">\n" +
                        "%s" +
                        "</body>\n" +
                        "</html>",
                table);
    }

    public static String makeListString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append("<li>").append(s);
        }
        return sb.toString();
    }

    public static String makeResumeHtmlTable(Resume resume) {
        String name = resume.getFullName();
        String contacts = getContacts(resume);
        String posDescr = "";
        String persDescr = "";
        String achDescr = "";
        String qualDescr = "";

        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            SectionType key = entry.getKey();
            Section value = entry.getValue();
            switch (key) {
                case OBJECTIVE:
                    posDescr = value.toString();
                    break;
                case PERSONAL:
                    persDescr = value.toString();
                    break;
                case ACHIEVEMENT:
                    achDescr = makeListString(((ListSection) value).getItems());
                    break;
                case QUALIFICATIONS:
                    qualDescr = makeListString(((ListSection) value).getItems());
                    break;
            }
        }

        return String.format(
                "<table align=\"center\" width=\"1024\" cellspacing=\"0\" cellpadding=\"1\">\n" +
                        "    <tr>\n" +
                        "        <td><h2>%s</h2></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "    <hr width=\"1024\">\n" +
                        "<table align=\"center\" width=\"1024\" cellspacing=\"0\" cellpadding=\"1\">\n" +
                        "    <tr>\n" +
                        "        <td><h2>Позиция</h2></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><h3>%s</h3></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><h2>Личные качества</h2></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><h2>Достижения</h2></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>\n" +
                        "            <ul type=\"circle\">\n" +
                        "%s" +
                        "            </ul>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><h2>Квалификация</h2></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>\n" +
                        "            <ul type=\"circle\">\n" +
                        "%s" +
                        "            </ul>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "</table>\n" +
                        "    <hr width=\"1536\" color=\"red\" width=\"300\">\n",
                name, contacts, posDescr, persDescr, achDescr, qualDescr);
    }

    public static String getContacts(Resume resume) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            ContactType key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case SKYPE:
                case EMAIL:
                case LINKEDIN:
                case GITHUB:
                case STACKOVERFLOW:
                case HOME_PAGE:
                    sb.append(key).append(": ").append("<a href=").append(value).append(">")
                            .append(value).append("</a>").append("<br/>");
                    break;
                default:
                    sb.append(key).append(": ").append(value).append("<br/>");
                    break;
            }
        }
        return sb.toString();
    }
}
