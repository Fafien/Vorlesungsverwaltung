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

import java.sql.Date;
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
import java.util.Calendar;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class LectureBean extends EntityBean<Lecture, Long> {

    public LectureBean() {
        super(Lecture.class);
    }
    
    public List<Lecture> searchAll(Course course) {
        if(course == null){
            return em.createQuery("SELECT l FROM Lecture l ")
                .getResultList();
        }
        else{
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                    + "l.course c "
                    + "WHERE c.courseName = :course ")
                    .setParameter("course", course.getCourseName())
                    .getResultList();
        }
    }

    public List<Lecture> searchDeleted(Course course) {
        if(course == null){
            return em.createQuery("SELECT l FROM Lecture l "
                + "WHERE l.deleted = :deleted ")
                .setParameter("deleted", true)
                .getResultList();
        }
        else{
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                    + "l.course c "
                    + "WHERE l.deleted = :deleted "
                    + "AND c.courseName = :course ")
                    .setParameter("deleted", true)
                    .setParameter("course", course.getCourseName())
                    .getResultList();
        }
    }

    public List<Lecture> searchBefore(Course course) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = new Date(c.getTimeInMillis());

        if(course == null){
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                + "l.appointment a "
                + "WHERE l.deleted = :deleted "
                + "AND a.date > :date")
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();
        }
        else{
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                    + "l.appointment a INNER JOIN l.course c "
                    + "WHERE l.deleted = :deleted "
                    + "AND c.courseName = :course "
                    + "AND a.date > :date")
                    .setParameter("deleted", false)
                    .setParameter("course", course.getCourseName())
                    .setParameter("date", date)
                    .getResultList();
        }
    }

    public List<Lecture> searchAfter(Course course) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = new Date(c.getTimeInMillis());
        
        if(course == null){
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                + "l.appointment a "
                + "WHERE l.deleted = :deleted "
                + "AND a.date < :date")
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();
        }
        else{
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                    + "l.appointment a INNER JOIN l.course c "
                    + "WHERE l.deleted = :deleted "
                    + "AND c.courseName = :course "
                    + "AND a.date < :date")
                    .setParameter("deleted", false)
                    .setParameter("course", course.getCourseName())
                    .setParameter("date", date)
                    .getResultList();
        }
    }

    public List<Lecture> searchToday(Course course) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date date = new Date(c.getTimeInMillis());
                
        if(course == null){
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                + "l.appointment a "
                + "WHERE l.deleted = :deleted "
                + "AND a.date = :date")
                .setParameter("deleted", false)
                .setParameter("date", date)
                .getResultList();
        }
        else{
            return em.createQuery("SELECT l FROM Lecture l INNER JOIN "
                + "l.appointment a INNER JOIN l.course c "
                + "WHERE l.deleted = :deleted "
                + "AND c.courseName = :course "
                + "AND a.date = :date")
                .setParameter("deleted", false)
                .setParameter("course", course.getCourseName())
                .setParameter("date", date)
                .getResultList();
        }
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
        
        CriteriaQuery<Lecture> query = cb.createQuery(Lecture.class);
        Root<Lecture> from = query.from(Lecture.class);
        query.select(from);

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
