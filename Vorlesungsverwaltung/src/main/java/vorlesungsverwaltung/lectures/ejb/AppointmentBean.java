
package vorlesungsverwaltung.lectures.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import vorlesungsverwaltung.common.ejb.EntityBean;
import vorlesungsverwaltung.lectures.jpa.Appointment;

/**
 *
 * @author jonas
 */
@Stateless
@RolesAllowed("app-user")
public class AppointmentBean extends EntityBean<Appointment, Long> {
    
    public AppointmentBean() {
        super(Appointment.class);
    }
}
