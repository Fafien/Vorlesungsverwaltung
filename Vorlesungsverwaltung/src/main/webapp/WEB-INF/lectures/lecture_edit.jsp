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
        <c:choose>
            <c:when test="${edit}">
                Vorlesung bearbeiten
            </c:when>
            <c:otherwise>
                Vorlesung anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/lectures/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="lecture_course">Kurs</label>
                <div class="side-by-side">
                    <select name="lecture_course">
                        <%-- <option value="">Keine Kategorie</option> --%>

                        <c:forEach items="${courses}" var="course">
                            <option value="${course.id}" ${lecture_form.values["lecture_course"][0] == course.id.toString() ? 'selected' : ''}>
                                <c:out value="${course.courseName}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="lecture_short_text">
                    Vorlesungsname:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="lecture_short_text" value="${lecture_form.values["lecture_short_text"][0]}">
                </div>
                
                <label for="lecture_lecturer">
                    Dozent/-in:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="lecture_lecturer" value="${lecture_form.values["lecture_lecturer"][0]}">
                </div>
                
                <label for="lecture_due_date">
                    Termin(e):
                </label>
                <c:set var="count" value="0" scope="page" />
                <c:forEach items="${lecture_form.values['lecture_due_date']}" var="loop">
                    <div class="side-by-side">
                        <input type="text" name="lecture_due_date<c:out value="${count}"/>" value="${lecture_form.values["lecture_due_date"][count]}">
                        <input type="text" name="lecture_due_time<c:out value="${count}"/>" value="${lecture_form.values["lecture_due_time"][count]}">
                    </div>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                </c:forEach>
                <div class="side-by-side">
                    <input type="text" name="lecture_due_date_new">
                    <input type="text" name="lecture_due_time_new">
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty errors}">
                <ul class="errors">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>