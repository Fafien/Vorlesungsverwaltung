/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
/* global antwort */

class Lectures {
    constructor(url) {
        this.url = url || "http://localhost:8080/vorlesungsverwaltung/api/Lectures/";
        this.username = "";
        this.password = "";
    }

    async function setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }
}

async function getLectures() {
    let search = document.getElementById("search");
    let resultDiv = document.getElementById("result");
    resultDiv.textContent = "Suche läuft …";
    resultDiv.classList.remove("unsichtbar");
    let url = "http://localhost:8080/vorlesungsverwaltung/api/Lectures/"
            + encodeURI(search.value);
    let antwort = await fetch(url, {
        method: "get",
        headers: {
            "accept": "application/json"
            "authorization": "Basic " + btoa(this.username + ":" + this.password)
        }
    });
    resultDiv.innerHTML = "";
    let lectures = await antwort.json();
    // Abgerufene Daten anzeigen
    lectures.forEach(lecture => {
        let html = "<div>" +
                "<b>Vorlesungsname: </b>" + lecture.lectureName + "<br/>" +
                "<b>Dozent/-in: </b>" + lecture.lecturer + "<br/>" +
                "<b>Kurs: </b>" + lecture.course + "<br/>" +
                "</div>";
        resultDiv.innerHTML += html;
    });
}

async function getTodaysLectures() {
    let resultDiv = document.getElementById("resultToday");
    resultDiv.classList.remove('unsichtbar');
    resultDiv.textContent = "Suche läuft …";
    let url = "http://localhost:8080/vorlesungsverwaltung/api/Lectures/today";
    let antwort = await fetch(url, {
        method: "get",
        headers: {
            "accept": "application/json"
            "authorization": "Basic " + btoa(this.username + ":" + this.password)
        }
    });
    resultDiv.innerHTML = "";
    let lectures = await antwort.json();
    // Abgerufene Daten anzeigen
    lectures.forEach(lecture => {
        let html = "<div>" +
                "<b>Vorlesungsname: </b>" + lecture.lectureName + "<br/>" +
                "<b>Dozent/-in: </b>" + lecture.lecturer + "<br/>" +
                "<b>Kurs: </b>" + lecture.course + "<br/>" +
                "</div>";
        resultDiv.innerHTML += html;
    });
}

async function getAllLectures() {
    let search = document.getElementById("search");
    let resultDiv = document.getElementById("result");
    resultDiv.textContent = "Suche läuft …";
    resultDiv.classList.remove("unsichtbar");
    let url = "http://localhost:8080/vorlesungsverwaltung/api/Lectures/all";
    let antwort = await fetch(url, {
        method: "get",
        headers: {
            "accept": "application/json"
            "authorization": "Basic " + btoa(this.username + ":" + this.password)
        }
    });
    resultDiv.innerHTML = "";
    let lectures = await antwort.json();
    // Abgerufene Daten anzeigen
    lectures.forEach(lecture => {
        let html = "<div>" +
                "<b>Vorlesungsname: </b>" + lecture.lectureName + "<br/>" +
                "<b>Dozent/-in: </b>" + lecture.lecturer + "<br/>" +
                "<b>Kurs: </b>" + lecture.course + "<br/>" +
                "</div>";
        resultDiv.innerHTML += html;
    });
}


