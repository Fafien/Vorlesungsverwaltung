/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package vorlesungsverwaltung.lectures.ejb;

import javax.ejb.Stateless;
import vorlesungsverwaltung.common.ejb.EntityBean;
import vorlesungsverwaltung.lectures.jpa.Course;

/**
 *
 * @author Licht
 */
@Stateless
public class CourseBean extends EntityBean<Course, Long> {

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public CourseBean() {
        super(Course.class);
    }
    //</editor-fold>

    public Course findByCourseName(String courseName) {
        return (Course) this.em.createQuery("SELECT c FROM Course c WHERE c.kursbezeichnung = :kursbezeichnung")
                .setParameter("kurzbezeichnung", courseName)
                .getSingleResult();
    }
}
