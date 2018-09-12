package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");

        Storage storage = Config.get().getStorage();

        if (uuid == null) {
            List<Resume> list = storage.getAllSorted();
            StringBuilder tableBuilder = new StringBuilder();
            for (Resume r : list) {
                tableBuilder.append(HtmlUtil.makeResumeHtmlTable(r));
            }
            response.getWriter().write(HtmlUtil.getPage(tableBuilder.toString()));

        } else {
            Resume resume = null;
            try {
                resume = storage.get(uuid);
            } catch (Exception e) {
                response.getWriter().write("Resume not exists");
            }
            response.getWriter().write(HtmlUtil.getPage(HtmlUtil.makeResumeHtmlTable(resume)));
        }
    }
}