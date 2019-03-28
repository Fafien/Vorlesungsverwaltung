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
        Kurse bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/course_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste der Vorlesungen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen einer neuen Kategorie --%>
            <div class="column margin">
                <label for="j_username">Neuer Kurs:</label>
                <input type="text" name="name" value="${course_form.values["name"][0]}">
                <button type="submit" name="action" value="create" class="icon-pencil">
                    Kurs anlegen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty course_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${course_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <%-- Vorhandene Kategorien --%>
            <c:choose>
                <c:when test="${empty courses}">
                    <p>
                        Es sind noch keine Kurse vorhanden.
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${courses}" var="course">
                                <input type="checkbox" name="course" id="${'course-'.concat(course.id)}" value="${course.id}" />
                                <label for="${'course-'.concat(course.id)}">
                                    <c:out value="${course.courseName}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte Kurse löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>