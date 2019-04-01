package vorlesungsverwaltung.lectures.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jonas
 */
@Entity
@Table(name = "VORLESUNGSVERWALTUNG_APPOINTMENT")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private Lecture lecture;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date date;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time time;

    public Appointment() {
    }

    public Appointment(Lecture lecture, Date date, Time time) {
        this.lecture = lecture;
        this.date = date;
        this.time = time;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
