class Lecture {

    constructor() {
        this.username = "";
        this.password = "";
    }

    async setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }

    async getLectures() {
        let search = document.getElementById("search");
        let resultDiv = document.getElementById("result");
        resultDiv.textContent = "Suche läuft …";
        resultDiv.classList.remove("unsichtbar");
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/"
                + encodeURI(search.value);

        if (this.username === "" || this.password === "") {
            alert("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein!");
            return;
        }
        let authorization = btoa(`${this.username}:${this.password}`);

        let antwort = await fetch(url, {
            method: "get",
            headers: {
                "accept": "application/json",
                "Authorization": `Basic ${authorization}`
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

    async getTodaysLectures() {
        let resultDiv = document.getElementById("resultToday");
        resultDiv.classList.remove('unsichtbar');
        resultDiv.textContent = "Suche läuft …";
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/today";

        if (this.username === "" || this.password === "") {
            alert("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein!");
            return;
        }
        let authorization = btoa(`${this.username}:${this.password}`);

        let antwort = await fetch(url, {
            method: "get",
            headers: {
                "accept": "application/json",
                "Authorization": `Basic ${authorization}`
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

    async getAllLectures() {
        let resultDiv = document.getElementById("resultAll");
        resultDiv.textContent = "Suche läuft …";
        resultDiv.classList.remove("unsichtbar");
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/all";

        if (this.username === "" || this.password === "") {
            alert("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein!");
            return;
        }
        let authorization = btoa(`${this.username}:${this.password}`);
        let antwort = await fetch(url, {
            method: "get",
            headers: {
                "accept": "application/json",
                "Authorization": `Basic ${authorization}`
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
}

window.addEventListener("load", function () {
    var lecture = new Lecture();
    document.getElementById("login").addEventListener("click", function () {
        lecture.setAuthData(document.getElementById('j_username').value, document.getElementById('j_password').value);
    });
    document.getElementById("searchLectures").addEventListener("click", function () {
        lecture.getLectures();
    });
    document.getElementById("searchLecturesToday").addEventListener("click", function () {
        lecture.getTodaysLectures();
    });
    document.getElementById("searchAllLectures").addEventListener("click", function () {
        lecture.getAllLectures();
    });
});