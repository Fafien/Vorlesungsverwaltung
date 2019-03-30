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

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vorlesungsverwaltung.lectures.ejb.CourseBean;
import vorlesungsverwaltung.lectures.ejb.LectureBean;
import vorlesungsverwaltung.lectures.jpa.Course;
import vorlesungsverwaltung.lectures.jpa.Lecture;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/lectures/list/"})
public class LectureListServlet extends HttpServlet {

    @EJB
    private CourseBean courseBean;
    
    @EJB
    private LectureBean lectureBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("courses", this.courseBean.findAllSorted());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCourse = request.getParameter("search_course");

        // Anzuzeigende Aufgaben suchen
        Course course = null;

        if (searchCourse != null) {
            try {
                course = this.courseBean.findById(Long.parseLong(searchCourse));
            } catch (NumberFormatException ex) {
                course = null;
            }
        }

        List<Lecture> lecture = this.lectureBean.searchByTextAndCourse(searchText, course);
        request.setAttribute("lectures", lecture);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/lectures/lecture_list.jsp").forward(request, response);
    }
}
