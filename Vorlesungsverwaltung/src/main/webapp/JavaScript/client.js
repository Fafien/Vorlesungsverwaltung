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

async function getLectures() {
    let search = document.getElementById("search");
    let resultDiv = document.getElementById("result");
    resultDiv.textContent = "Suche läuft …";
    resultDiv.classList.remove("unsichtbar");
    //TODO: URL an unseren Service anpassen
    let url = "http://localhost:8080/vorlesungsverwaltung/api/Lectures/"
            + encodeURI(search.value);
    let antwort = await fetch(url, {
        method: "get",
        headers: {
            "accept": "application/json"
        }
    });
    resultDiv.innerHTML = "";
    let lectures = await antwort.json();
    // Abgerufene Daten anzeigen
    lectures.forEach(lecture => {
        //TODO: Pro Eigenschaft Zeile nach Pattern erstellen
        let html = "<div>" +
                "<b>Name der Eigenschaft:</b>" + lecture.lectureName + "<br/>" +
                "</div>";
        resultDiv.innerHTML += html;
    });
}

async function getTodaysLectures() {
    let resultDiv = document.getElementById("resultToday");
    resultDiv.classList.remove('unsichtbar');
    resultDiv.textContent = "Suche läuft …";
    //TODO: URL an unseren Service anpassen
    let url = "http://localhost:8080/vorlesungsverwaltung/api/Lectures/today";
    let antwort = await fetch(url, {
        method: "get",
        headers: {
            "accept": "application/json"
        }
    });
    resultDiv.innerHTML = "";
    let lectures = await antwort.json();
    // Abgerufene Daten anzeigen
    lectures.forEach(lecture => {
        //TODO: Pro Eigenschaft Zeile nach Pattern erstellen
        let html = "<div>" +
                "<b>Name der Eigenschaft:</b>" + lecture.lectureName + "<br/>" +
                "</div>";
        resultDiv.innerHTML += html;
    });
}


