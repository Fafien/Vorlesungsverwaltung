<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>

<template:base>
    <jsp:attribute name="title">
        Benutzerdaten ändern
    </jsp:attribute>

    <jsp:attribute name="head">

    </jsp:attribute>

    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <input type="hidden" name="csrf_token" value="${csrf_token}">
                <%-- Eingabefelder --%>
                <label for="signup_username">
                    Benutzername:
                </label>
                <div class="side-by-side">
                    <input type="text" class="form-control" name="username" value="${edit_form.values["username"][0]}" placehousernamelder="Benutzername" required="required" autofocus="autofocus" readonly>
                </div>
                <label for="newPassword">
                    Passwort:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="password" name="newPassword" >
                </div>
                <label for="newPasswordConfirm">
                    Passwort (Wdh.)
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="password" name="newPasswordConfirm" >
                </div>
                <label for="firstname">
                    Vorname:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="firstname" value="${signup_form.values["firstname"][0]}" >
                </div>
                <label for="firstname">
                    Nachname:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="lastname" value="${signup_form.values["lastname"][0]}" >
                </div>
                <label for="coursename">
                    Kurs:
                </label>
                <div class="side-by-side">
                    <select class="js-example-basic-multiple col-md-10 form-control" name="coursename" id="courseSelect" required="required" placeholder="Kurs">
                        <!-- Leeres Auswahlfeld, damit der Kurs nicht immer zwingend geändert werden muss !-->
                        <option></option>
                        <c:forEach items="${courses}" var="course">
                            <option value="${course.courseName}" ${course.courseName eq "${edit_form.values['currentCourseName'][0]}" ? ' selected' : ''}>${course.courseName}</option>
                        </c:forEach>
                    </select>
                </div>
                <label for="oldPassword">
                    Aktuelles Passwort:
                </label>
                <div class="side-by-side">
                    <input type="password" name="oldPassword">
                    <small id="confirmHelp" class="form-text text-muted">Bitte geben Sie Ihr altes Passwort ein, um die Änderungen zu bestätigen!</small>
                </div>
                <%-- Button zum Abschicken --%>
                <input class="btn btn-primary btn-block" type="submit" value="Benutzerdaten ändern">
            </form>
            <%-- Fehlermeldungen --%>
            <c:if test="${!empty edit_form.errors}">
                <ul class="errors">
                    <c:forEach items="${edit_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </div>

    </jsp:attribute>
</template:base>
