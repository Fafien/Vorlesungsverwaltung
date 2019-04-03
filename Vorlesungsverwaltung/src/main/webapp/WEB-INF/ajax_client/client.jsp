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

        <h1>Alle meine Vorlesungen</h1>

        <input type="text" id="search" value="">
        <button id="searchLectures">Abrufen</button>

        <div>
            <div id="result" class="unsichtbar"></div>
        </div>
        <h1>Alle heutigen Vorlesungen</h1>

        <button id="searchLecturesToday">Abrufen</button>

        <div>
            <div id="resultToday" class="unsichtbar"></div>
        </div>

        <h1>Alle Vorlesungen</h1>

        <button id="searchAllLectures">Abrufen</button>

        <div>
            <div id="resultAll" class="unsichtbar"></div>
        </div>
    </jsp:attribute>
</template:base>
