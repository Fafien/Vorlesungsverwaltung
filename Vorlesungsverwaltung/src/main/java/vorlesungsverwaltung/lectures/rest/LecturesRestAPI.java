
package vorlesungsverwaltung.lectures.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author Licht
 */
@ApplicationPath("api")
public class LecturesRestAPI extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(LectureResource.class);

        return resources;
    }
}
