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
        <script>
            lecture = new Lecture();
            lecture.setAuthData("username", "password");
        </script>
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

        <button class="icon-login" onclick="lecture.setAuthData(document.getElementById('j_username').value,document.getElementById('j_password').value)">
            Einloggen
        </button>
        
        <h1>Alle meine Vorlesungen</h1>

        <input type="text" id="search" value="">
        <button onclick="getLectures()">Abrufen</button>

        <div>
            <div id="result" class="unsichtbar"></div>
        </div>
        <h1>Alle heutigen Vorlesungen</h1>

        <button onclick="getTodaysLectures()">Abrufen</button>

        <div>
            <div id="resultToday" class="unsichtbar"></div>
        </div>
        
        <h1>Alle Vorlesungen</h1>

        <button onclick="getAllLectures()">Abrufen</button>

        <div>
            <div id="resultToday" class="unsichtbar"></div>
        </div>
    </jsp:attribute>
</template:base>
