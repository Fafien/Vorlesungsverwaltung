Vorlesungsverwaltung
=========================

Kurzbeschreibung
----------------

Diese Anwendung wurde im Rahmen der Vorlesung "Verteilte Systeme" an der DHBW 
Karlsruhe realisiert. Es handelt ist um eine in Java realisierte, serverseitige
MVC-Webanwendung. 
Der Zweck der Anwendung ist eine Verwaltung von Vorlesungen und Terminen, die
einem Kurs zugeteilt werden können.
Die Anwendung setzt dabei ganz klassisch auf der „Jakarta Enterprise Edition”
auf und läuft daher in einem speziell dafür ausgelegten Applikationsserver. 
Sämtliche Anwendungslogik wird dabei vom Server implementiert, so dass für 
jedes URL-Pattern der Anwendung ein komplett serverseitig generierte HTML-
Seite abgerufen und im Browser dargestellt wird.

Verwendete Technologien
-----------------------

Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung
