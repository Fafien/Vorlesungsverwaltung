<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>

<template:base>
    <jsp:attribute name="title">
        REST-Webservice-Client: AJAX-Aufruf
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/ajax_client/style.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <h1>Login</h1>
        <label for="j_username">
            Benutzername:
            <span class="required">*</span>
        </label>
        <input type="text" name="j_username" id="j_username">

        <label for="j_password">
            Passwort:
            <span class="required">*</span>
        </label>
        <input type="password" name="j_password" id="j_password">

        <button class="icon-login" id="login">
            Einloggen
        </button>
        </br>

        <label>Alle meine Vorlesungen</label>
        <input type="text" id="search" value="">
        <button id="searchLectures">Abrufen</button>

        </br>
        <label>Alle heutigen Vorlesungen: </label>
        <button id="searchLecturesToday">Abrufen</button>

        </br>
        <label>Alle Vorlesungen: </label>
        <button id="searchAllLectures">Abrufen</button>

        <div>
            <div id="result" class="unsichtbar"></div>
        </div>
    </jsp:attribute>
</template:base>
