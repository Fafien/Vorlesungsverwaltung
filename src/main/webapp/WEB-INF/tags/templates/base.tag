<%@tag pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="title"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="menu" fragment="true"%>
<%@attribute name="content" fragment="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />

        <title>Vorlesungsliste: ${title}</title>

        <link rel="shortcut icon" href="<c:url value="/img/favicon.png"/>">

        <link rel="stylesheet" href="<c:url value="/fontello/css/fontello.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/fontawesome/css/all.min.css"/>">
        
        <script type="text/javascript" src="<c:url value="/JavaScript/client.js"/>"></script>

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <%-- Kopfbereich --%>
        <header>
            <%-- Titelzeile --%>
            <div id="titlebar">
                <div class="appname">
                    Vorlesungsliste
                </div>
                <div class="content">
                    ${title}
                </div>
            </div>

            <%-- Menü --%>
            <div id="menubar">
                <jsp:invoke fragment="menu"/>

                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <div class="menuitem">
                        <a href="<c:url value="/logout/"/>" class="icon-logout">Logout ${pageContext.request.userPrincipal.name}</a>
                    </div>
                </c:if>
            </div>
        </header>

        <%-- Hauptinhalt der Seite --%>
        <main>
            <jsp:invoke fragment="content"/>
        </main>
    </body>
</html>