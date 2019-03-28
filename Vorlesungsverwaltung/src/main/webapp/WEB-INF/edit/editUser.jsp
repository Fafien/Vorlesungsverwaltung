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
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Benutzerdaten ändern</div>
                <div class="card-body">
                    <form method="post" class="stacked">
                        <%-- Eingabefelder --%>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" name="username" value="${edit_form.values["username"][0]}" placehousernamelder="Benutzername" required="required" autofocus="autofocus" readonly>
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control mr-1" name="newPassword" value="${edit_form.values["password1"][0]}" placeholder="Passwort" required="required">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" name="newPasswordConfirm" value="${edit_form.values["password2"][0]}" placeholder="Passwort wiederholen" required="required">
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user-circle"></i></span>
                            </div>
                            <input type="text" class="form-control" name="firstname" value="${edit_form.values["firstname"][0]}" placeholder="Vorname" required="required">
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user-circle"></i></span>
                            </div>
                            <input type="text" class="form-control" name="lastname" value="${edit_form.values["lastname"][0]}" placeholder="Nachname" required="required">
                        </div>
                        <div class="form-group">
                            <select class="js-example-basic-multiple col-md-10 form-control" name="coursename" id="courseSelect" required="required" placeholder="Kurs">
                                <c:forEach items="${courses}" var="course">
                                    <option value="${course}" ${course.courseName eq "${edit_form.values['username'][0]}" ? ' selected' : ''}>${course.courseName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <small id="confirmHelp" class="form-text text-muted">Bitte geben Sie Ihr altes Passwort ein, um die Änderungen zu bestätigen!</small>
                            <input type="password" class="form-control mr-1" name="oldPassword" value="${edit_form.values["password1"][0]}" placeholder="Passwort" required="required">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                        </div>
                        <%-- Button zum Abschicken --%>
                        <input class="btn btn-primary btn-block" type="submit" value="Registrieren">
                    </form>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty edit_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${edit_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</template:base>
