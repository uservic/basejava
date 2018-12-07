<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="inner">
        <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"> </a>
        </h2>
        <p style="text-align:left">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
            </c:forEach>
        </p>
        <hr size="2" noshade color="#b6c0c7">

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                      ru.javawebinar.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <h2 style="text-align:left">${type.title}</h2>

            <div class="left">

            <c:choose>

                <c:when test="${type == (SectionType.OBJECTIVE)
            || type == (SectionType.PERSONAL)}">
                    <%=sectionEntry.getValue()%>
                </c:when>

                <c:when test="${type == (SectionType.ACHIEVEMENT)
            || type == (SectionType.QUALIFICATIONS)}">
                    <div class="left">
                        <c:forEach var="textItem" items="<%=((ListSection) sectionEntry.getValue()).getItems()%>">
                            <jsp:useBean id="textItem" type="java.lang.String"/>
                            <li>
                                    ${textItem}
                            </li>
                        </c:forEach>
                    </div>
                </c:when>

                <c:when test="${type == (SectionType.EXPERIENCE)
            || type == (SectionType.EDUCATION)}">
                    <div style="padding-left: 15px">
                        <c:forEach var="orgItem" items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizations()%>">
                            <jsp:useBean id="orgItem" type="ru.javawebinar.basejava.model.Organization"/>

                            <c:if test="${empty orgItem.orgUrl}">
                                <h3>${orgItem.orgName}</h3>
                            </c:if>
                            <c:if test="${not empty orgItem.orgUrl}">
                                <h3><a href="http://${orgItem.orgUrl}">${orgItem.orgName}</a></h3>
                            </c:if>

                            <c:forEach var="positionItem" items="${orgItem.positions}">
                                <jsp:useBean id="positionItem"
                                             type="ru.javawebinar.basejava.model.Organization.Position"/>

                                <table>
                                    <tr>
                                        <td rowspan="2" valign="top">
                                                ${DateUtil.parse(positionItem.startDate)}
                                            - ${DateUtil.parse(positionItem
                                                .endDate)}</td>
                                        <td style="padding-left: 15px"><b>${positionItem.position}</b></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-left: 15px">${positionItem.description}</td>
                                    </tr>
                                </table>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </c:when>

            </c:choose>

            </div>

        </c:forEach>
        <hr size="2" noshade color="#b6c0c7">
        <button onclick="window.history.back()">К списку резюме</button>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>