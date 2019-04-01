/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package vorlesungsverwaltung.lectures.web;

import vorlesungsverwaltung.common.web.FormValues;
import vorlesungsverwaltung.common.ejb.ValidationBean;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vorlesungsverwaltung.lectures.ejb.CourseBean;
import vorlesungsverwaltung.lectures.jpa.Course;
import vorlesungsverwaltung.lectures.jpa.Lecture;
import vorlesungsverwaltung.lectures.ejb.LectureBean;

/**
 * Seite zum Anzeigen und Bearbeiten der Kategorien. Die Seite besitzt ein
 * Formular, mit dem ein neue Kategorie angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Kategorien verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/course/"})
public class CourseListServlet extends HttpServlet {

    @EJB
    CourseBean courseBean;

    @EJB
    LectureBean lectureBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Kategorien ermitteln
        request.setAttribute("courses", this.courseBean.findAll());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/lectures/course_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("course_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createCourse(request, response);
                break;
            case "delete":
                this.deleteCourse(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neuen Kurs anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String courseName = request.getParameter("name");

        Course course = new Course(courseName);
        List<String> errors = this.validationBean.validate(course);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.courseBean.saveNew(course);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("course_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kurse und die dazugehörigen Vorlesungen
     * löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] courseIds = request.getParameterValues("course");

        if (courseIds == null) {
            courseIds = new String[0];
        }

        for (String courseId : courseIds) {
            Course course;

            try {
                course = this.courseBean.findById(Long.parseLong(courseId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (course == null) {
                continue;
            }

            List<Lecture> lectures = this.lectureBean.searchByCourse(course);

            if (lectures != null) {
                lectures.forEach((Lecture lecture) -> {
                    lecture.setCourse(null);
                    this.lectureBean.delete(lecture);
                });
            }
            this.courseBean.delete(course);
        }
        response.sendRedirect(request.getRequestURI());
    }

}
