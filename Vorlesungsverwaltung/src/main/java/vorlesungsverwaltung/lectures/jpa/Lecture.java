package vorlesungsverwaltung.lectures.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Fabian Hupe
 */
@Entity
@Table(name = "VORLESUNGSVERWALTUNG_LECTRUE")
public class Lecture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "lecture_ids")
    @TableGenerator(name = "lecture_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @Column(length = 25)
    @NotNull(message = "Der Vorlesungsname darf nicht leer sein.")
    @Size(min = 1, max = 25, message = "Die Bezeichnung muss zwischen ein und 25 Zeichen lang sein.")
    private String lectureName;

    @Column(length = 25)
    @NotNull(message = "Es muss ein Dozent eingetragen sein.")
    @Size(min = 1, max = 25, message = "Die Bezeichnung muss zwischen ein und 25 Zeichen lang sein.")
    private String lecturer;

    @OneToMany
    private List<Appointment> appointment;

    public Lecture() {
    }

    public Lecture(Course course, String lectureName, String lecturer, List<Appointment> appointment) {
        this.course = course;
        this.lectureName = lectureName;
        this.lecturer = lecturer;
        this.appointment = appointment;
    }

    public long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }
}
