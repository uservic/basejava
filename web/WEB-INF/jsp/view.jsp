<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
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
    <div style="padding-left: 50px">
        <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"> </a>
        </h2>
        <p>
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
            <h2>${sectionEntry.key.title}</h2>
            <c:choose>
                <c:when test="${sectionEntry.key.equals(SectionType.OBJECTIVE)
            || sectionEntry.key.equals(SectionType.PERSONAL)}">
                    <%=sectionEntry.getValue()%>
                </c:when>

                <c:when test="${sectionEntry.key.equals(SectionType.ACHIEVEMENT)
            || sectionEntry.key.equals(SectionType.QUALIFICATIONS)}">
                    <c:set var="listSection" value="<%=((ListSection) sectionEntry.getValue()).getItems()%>"/>
                    <div>
                        <c:forEach var="textItem" items="${listSection}">
                            <jsp:useBean id="textItem" type="java.lang.String"/>
                            <li>
                                    ${textItem}
                            </li>
                        </c:forEach>
                    </div>
                </c:when>

                <c:when test="${sectionEntry.key.equals(SectionType.EXPERIENCE)
            || sectionEntry.key.equals(SectionType.EDUCATION)}">
                    <c:set var="orgSection" value="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizations()%>"/>
                    <div style="padding-left: 15px">
                        <c:forEach var="orgItem" items="${orgSection}">
                            <jsp:useBean id="orgItem" type="ru.javawebinar.basejava.model.Organization"/>

                            <h3><a href="http://${orgItem.orgUrl}">${orgItem.orgName}</a></h3>
                            <c:forEach var="positionItem" items="${orgItem.positions}">
                                <jsp:useBean id="positionItem"
                                             type="ru.javawebinar.basejava.model.Organization.Position"/>

                                <table>
                                    <tr>
                                        <td rowspan="2" valign="top">
                                                ${DateUtil.parse(positionItem.startDate)}
                                            - ${DateUtil.parse(positionItem
                                                .endDate)}</td>
                                        <td align="right"><b>${positionItem.position}</b><br/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">${positionItem.description}</td>
                                    </tr>
                                </table>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </c:when>

            </c:choose>
        </c:forEach>
        <hr size="2" noshade color="#b6c0c7">
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>