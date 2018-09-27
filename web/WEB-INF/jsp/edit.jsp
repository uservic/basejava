<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.model.Section" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div style="padding-left: 50px">
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <h2>Имя:</h2>
            <dl>
                <input type="text" name="fullName" size="50" value="${resume.fullName}">
            </dl>
            <h2>Контакты:</h2>
            <div style="padding-left: 15px">
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    ${type.title}
                    <dl>
                        <input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
                    </dl>
                </c:forEach>
            </div>

            <hr>
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Назад</button>
            <hr>

            <h2>Секции:</h2>
            <div style="padding-left: 15px">
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <h3>${type.title}</h3>
                    <c:set var="section" value="${resume.getSection(type)}"/>
                    <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
                    <c:choose>
                        <c:when test="${type == (SectionType.OBJECTIVE)
                                        || type == (SectionType.PERSONAL)}">
                            <textarea name="${type.name()}" rows="10" cols="70"> <%=section%></textarea>
                        </c:when>

                        <c:when test="${type == (SectionType.ACHIEVEMENT)
                                        || type == (SectionType.QUALIFICATIONS)}">
                        <textarea name="${type.name()}" rows="15" cols="70"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                        </c:when>

                        <c:when test="${type == (SectionType.EXPERIENCE)
                                        || type == (SectionType.EDUCATION)}">
                            <c:set var="orgs" value="<%=((OrganizationSection) section).getOrganizations()%>"/>
                            <input type="hidden" name="${type.name()}" value="${orgs.size()}">
                            <c:forEach var="org" items="${orgs}" varStatus="orgCounter">
                                <input type="hidden" name="${type}orgListSize" value="${orgs.size()}">
                                <dl>
                                    <dt><h4 style="margin-bottom: 0px">Название организации:</h4></dt>
                                    <dd><input type="text" name='${type}orgName${orgCounter.index}' size=80 value="${org.orgName}"></dd>
                                    <dt><h4 style="margin-bottom: 0px">Сайт организации:</h4></dt>
                                    <dd><input type="text" name='${type}orgUrl${orgCounter.index}' size=80 value="${org.orgUrl}"></dd>
                                </dl>
                                <div style="padding-left: 30px">

                                    <input type="hidden" name="${type}posListSize${orgCounter.index}" value="${org.positions.size()}">
                                    <c:forEach var="pos" items="${org.positions}" varStatus="posCounter">
                                        <jsp:useBean id="pos"
                                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                                        <dl>
                                            <dt><h5>Название позиции</h5></dt>
                                            <div style="padding-left: 30px">
                                                <dd>
                                                    <input type="text" name="${type}${orgCounter.index}posName${posCounter.index}" size=30
                                                           value="${pos.position}">
                                                </dd>
                                                <dt>Начальная дата:</dt>
                                                <dd>
                                                    <input type="text" name="${type}${orgCounter.index}startDate${posCounter.index}" size=30
                                                           value="<%=DateUtil.parse(pos.getStartDate())%>" placeholder="ММ/ГГГГ"
                                                           pattern="(0[1-9]|1[0-2])/[1-2][0-9]{3}|Сейчас"
                                                    title="Введите дату в фомате ММ/ГГГГ">
                                                </dd>
                                                <dt>Конечная дата:</dt>
                                                <dd>
                                                    <input type="text" name="${type}${orgCounter.index}endDate${posCounter.index}" size=30
                                                           value="<%=(DateUtil.parse(pos.getEndDate()))%>" placeholder="ММ/ГГГГ"
                                                           pattern="((0[1-9]|1[0-2])/[1-2][0-9]{3})|Сейчас"
                                                           title="Введите дату в фомате ММ/ГГГГ или 'Сейчас'">
                                                </dd>
                                                <dt>Описание позиции</dt>
                                                <dd>
                                                    <textarea name="${type}${orgCounter.index}posDescr${posCounter.index}" rows="3"
                                                              cols="70">${pos.description}</textarea>
                                                </dd>
                                            </div>
                                        </dl>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </div>
            <hr>
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Назад</button>
            <hr>
        </form>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>