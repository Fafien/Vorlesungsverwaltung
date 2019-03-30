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

import vorlesungsverwaltung.common.web.WebUtils;
import vorlesungsverwaltung.common.web.FormValues;
import vorlesungsverwaltung.common.ejb.UserBean;
import vorlesungsverwaltung.common.ejb.ValidationBean;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vorlesungsverwaltung.lectures.ejb.AppointmentBean;
import vorlesungsverwaltung.lectures.ejb.CourseBean;
import vorlesungsverwaltung.lectures.ejb.LectureBean;
import vorlesungsverwaltung.lectures.jpa.Appointment;
import vorlesungsverwaltung.lectures.jpa.Lecture;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/lectures/lecture/*")
public class LectureEditServlet extends HttpServlet {

    @EJB
    LectureBean lecturebean;

    @EJB
    CourseBean coursebean;
    
    @EJB
    AppointmentBean appointmentbean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("courses", this.coursebean.findAllSorted());

        Lecture lecture = this.getRequestedLecture(request);
        request.setAttribute("edit", lecture.getId() != 0);
                                
        if (session.getAttribute("lecture_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("lecture_form", this.createLectureForm(lecture));
        }

        // Weiterleitung an die JSP
        request.getRequestDispatcher("/WEB-INF/lectures/lecture_edit.jsp").forward(request, response);
        
        session.removeAttribute("lecture_form");
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
            case "save":
                this.saveLecture(request, response);
                break;
            case "delete":
                this.deleteLecture(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveLecture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String lectureCourse = request.getParameter("lecture_course");
        String lectureDueDate = request.getParameter("lecture_due_date");
        String lectureDueTime = request.getParameter("lecture_due_time");
        String lectureName = request.getParameter("lecture_short_text");
        String lecturer = request.getParameter("lecture_lecturer");

        Lecture lecture = this.getRequestedLecture(request);

        if (lectureCourse != null && !lectureCourse.trim().isEmpty()) {
            try {
                lecture.setCourse(this.coursebean.findById(Long.parseLong(lectureCourse)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(lectureDueDate);
        Time dueTime = WebUtils.parseTime(lectureDueTime);

        List<Appointment> appointments = new ArrayList<Appointment>();
        Appointment appointment = new Appointment();
        
        if (dueDate != null) {
            appointment.setDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            appointment.setTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }
        
        appointments.add(appointment);
        lecture.setLectureName(lectureName);
        lecture.setLecturer(lecturer);
        lecture.setAppointment(appointments);
        
        this.validationBean.validate(lecture, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.lecturebean.update(lecture);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/lectures/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("lecture_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteLecture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Lecture lecture = this.getRequestedLecture(request);
        this.lecturebean.delete(lecture);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/lectures/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Lecture getRequestedLecture(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Lecture lecture = new Lecture();
        Appointment appointment = new Appointment();
        appointment.setLecture(lecture);
        appointment.setDate(new Date(System.currentTimeMillis()));
        appointment.setTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String lectureId = request.getPathInfo();

        if (lectureId == null) {
            lectureId = "";
        } else {
            lectureId = lectureId.substring(1);
        }

        if (lectureId.endsWith("/")) {
            lectureId = lectureId.substring(0, lectureId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            lecture = this.lecturebean.findById(Long.parseLong(lectureId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return lecture;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param lecture Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createLectureForm(Lecture lecture) {
        Map<String, String[]> values = new HashMap<>();

        if (lecture.getCourse() != null) {
            values.put("lecture_course", new String[]{
                "" + lecture.getCourse().getId()
            });
        }
        
//es können mehrere Termine pro Vorlesung sein!
        values.put("lecture_due_date", new String[]{
//            WebUtils.formatDate(lecture.getAppointment().date)
        });

        values.put("lecture_due_time", new String[]{
//            WebUtils.formatTime(lecture.getDueTime())
        });

        values.put("lecture_short_text", new String[]{
            lecture.getLectureName()
        });
        
        values.put("lecture_lecturer", new String[]{
            lecture.getLecturer()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}

