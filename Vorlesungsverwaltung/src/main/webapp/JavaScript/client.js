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
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/"
                + encodeURI(search.value);
        this.getResults(url);
    }

    async getTodaysLectures() {
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/today";
        this.getResults(url);
    }

    async getAllLectures() {
        let url = "https://localhost:8443/vorlesungsverwaltung/api/Lectures/all";
        this.getResults(url);
    }

    async getResults(url) {
        if (this.username === "" || this.password === "") {
            alert("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein!");
            return;
        }
        try {
            let authorization = btoa(`${this.username}:${this.password}`);
            let antwort = await fetch(url, {
                method: "get",
                headers: {
                    "accept": "application/json",
                    "Authorization": `Basic ${authorization}`
                }
            });
            let lectures = await antwort.json();
            this.printResults(lectures);
        } catch (e) {
            alert("Konnte keine Ergebnisse vom REST-Service bekommen. Sind Ihre Benutzerdaten korrekt?");
            return;
        }
    }

    async printResults(lectures) {
        let resultDiv = document.getElementById("result");
        resultDiv.classList.remove("unsichtbar");
        resultDiv.innerHTML = "";
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
