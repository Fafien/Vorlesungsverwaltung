
package vorlesungsverwaltung.lectures.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import vorlesungsverwaltung.lectures.ejb.CourseBean;
import vorlesungsverwaltung.lectures.ejb.LectureBean;
import vorlesungsverwaltung.lectures.jpa.Course;
import vorlesungsverwaltung.lectures.jpa.Lecture;

/**
 *
 * @author Licht
 */
@Path("Lectures")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LectureResource {

    @EJB
    private LectureBean lectureBean;

    @EJB
    private CourseBean courseBean;

    @GET
    @Path("{courseName}")
    public List<Lecture> findByCourseName(@PathParam("courseName") String courseName) {
        Course course = this.courseBean.findByCourseName(courseName);
        List<Lecture> lectures = this.lectureBean.searchByCourse(course);
        return lectures;
    }

    @GET
    @Path("/all")
    public List<Lecture> findLectures() {
        return this.lectureBean.findAll();
    }

    @GET
    @Path("/today")
    public List<Lecture> findTodaysLectures() {
        List<Lecture> lectures = this.lectureBean.searchToday(null);
        return lectures;
    }
}
