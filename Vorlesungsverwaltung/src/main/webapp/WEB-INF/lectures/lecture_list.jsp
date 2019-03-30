<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Vorlesungen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/lectures/lecture/new/"/>">Vorlesung anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/courses/"/>">Kurs anlegen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_course">
                <option value="">Alle Kurse</option>

                <c:forEach items="${courses}" var="course">
                    <option value="${course.id}" ${param.search_course == course.id ? 'selected' : ''}>
                        <c:out value="${course.courseName}" />
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty lectures}">
                <p>
                    Es wurden keine Vorlesungen gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="vorlesungsverwaltung.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Vorlesungsname</th>
                            <th>Kurs</th>
                            <th>Dozent</th>
                            <th>Termin</th>
                        </tr>
                    </thead>
                    <c:forEach items="${lectures}" var="lecture">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/lectures/lecture/${lecture.id}/"/>">
                                    <c:out value="${lecture.lectureName}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${lecture.course.courseName}"/>
                            </td>
                            <td>
                                <c:out value="${lecture.lecturer}"/>
                            </td>
                            <%-- es können mehrere Termine pro Vorlesung sein --%>
                            <td>
                                <c:out value="${utils.formatDate(lecture.appointment.date)}"/>
                                <c:out value="${utils.formatTime(lecture.appointment.time)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>