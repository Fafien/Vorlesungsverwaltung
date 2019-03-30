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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
                + "ORDER BY l.appointment.date, l.appointment.time")
                .setParameter("course", course)
                .getResultList();
    }
    
    public List<Lecture> searchByTextAndCourse(String search, Course course) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Task t
        CriteriaQuery<Lecture> query = cb.createQuery(Lecture.class);
        Root<Lecture> from = query.from(Lecture.class);
        query.select(from);

        // WHERE l.lectureName LIKE :search
        Predicate p = cb.conjunction();
        
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("lectureName"), "%" + search + "%"));
            query.where(p);
        }
        
        if (course != null) {
            p = cb.and(p, cb.equal(from.get("course"), course));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
}
