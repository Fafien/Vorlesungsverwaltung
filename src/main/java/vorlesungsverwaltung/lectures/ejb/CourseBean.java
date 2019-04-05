
package vorlesungsverwaltung.lectures.ejb;

import java.util.List;
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
        return (Course) this.em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName")
                .setParameter("courseName", courseName)
                .getSingleResult();
    }
    
    public List<Course> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Course c ORDER BY c.courseName").getResultList();
    }
}
