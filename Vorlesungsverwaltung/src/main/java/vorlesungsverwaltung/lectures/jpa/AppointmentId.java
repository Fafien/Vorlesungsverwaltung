
package vorlesungsverwaltung.lectures.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jonas
 */
public class AppointmentId implements Serializable {
    
    private Lecture lecture;
    private Date date;
    private Time time;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.lecture);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppointmentId other = (AppointmentId) obj;
        if (!Objects.equals(this.lecture, other.lecture)) {
            return false;
        }
        return true;
    }
    
}
