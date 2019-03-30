/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package vorlesungsverwaltung.lectures.ejb;

import java.util.Date;
import vorlesungsverwaltung.common.ejb.EntityBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import vorlesungsverwaltung.lectures.jpa.Lecture;
import vorlesungsverwaltung.lectures.jpa.Course;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class LectureBean extends EntityBean<Lecture, Long> {

    public LectureBean() {
        super(Lecture.class);
    }

    public List<Lecture> searchDeleted(Course course) {

        return em.createQuery("SELECT l FROM Lecture l, Appointment a "
                + "WHERE l.deleted = :deleted "
                + "AND l.course = :course "
                + "ORDER BY a.date, a.time")
                .setParameter("course", course)
                .setParameter("deleted", true)
                .getResultList();

    }

    public List<Lecture> searchBefore(Course course) {

        Date date = new Date();

        return em.createQuery("SELECT l FROM Lecture l "
                + "WHERE l.course = :course "
                + "AND l.deleted = :deleted "
                + "AND l.appointment.date < :date "
                + "ORDER BY l.appointment.date, l.appointment.time")
                .setParameter("course", course)
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();

    }

    public List<Lecture> searchAfter(Course course) {

        Date date = new Date();

        return em.createQuery("SELECT l FROM Lecture l "
                + "WHERE l.course = :course "
                + "AND l.deleted = :deleted "
                + "AND l.appointment.date > :date "
                + "ORDER BY l.appointment.date, l.appointment.time")
                .setParameter("course", course)
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();

    }

    public List<Lecture> searchToday(Course course) {

        Date date = new Date();

        return em.createQuery("SELECT l FROM Lecture l "
                + "WHERE l.course = :course "
                + "AND l.deleted = :deleted "
                + "AND l.appointment.date = :date "
                + "ORDER BY l.appointment.date, l.appointment.time")
                .setParameter("course", course)
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();

    }

    public List<Lecture> searchByCourse(Course course) {
        return em.createQuery("SELECT l FROM Lecture l WHERE l.course = :course"
                + "ORDER BY l.appiontment.date, l.appointment.time")
                .setParameter("course", course)
                .getResultList();
    }
}
