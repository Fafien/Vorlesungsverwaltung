
package vorlesungsverwaltung.lectures.ejb;

import vorlesungsverwaltung.common.web.WebUtils;
import vorlesungsverwaltung.dashboard.ejb.DashboardContentProvider;
import vorlesungsverwaltung.dashboard.ejb.DashboardSection;
import vorlesungsverwaltung.dashboard.ejb.DashboardTile;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vorlesungsverwaltung.common.ejb.UserBean;
import vorlesungsverwaltung.lectures.jpa.Course;


@Stateless(name = "lectures")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    private CourseBean courseBean;

    @EJB
    private LectureBean lectureBean;
    
    @EJB
    private UserBean userBean;

    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        DashboardSection section = this.createSection(null);
        sections.add(section);
        
        List<Course> courseList = courseBean.findAll();
        
        if(userBean.getCurrentUser().getCourse() != null){
            section = this.createSection(userBean.getCurrentUser().getCourse());
            sections.add(section);
        }
        else{
            for(Course course : courseList){
                section = this.createSection(course);
                sections.add(section);
            }
        }
    }

    private DashboardSection createSection(Course course) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";
        String status = new String();

        if (course != null) {
            section.setLabel(course.getCourseName());
        } else {
            section.setLabel("Alle Kurse");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(course, "all", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for(int i = 0; i < 3; i++){
            String icon = "";
            
            switch (i) {
                case 0:
                    icon = "doc-text";
                    status = "today";
                    break;
                case 1:
                    icon = "rocket";
                    status = "before";
                    break;
                case 2:
                    icon = "ok";
                    status = "after";
                    break;
            }
            
            String cssClass1 = cssClass + " status-" + status;
            
            tile = this.createTile(course, status, cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    private DashboardTile createTile(Course course, String status, String cssClass, String icon) {
        int amount = 0;
        String label = new String("");
        switch(status){
            case "all":
                amount = lectureBean.searchAll(course).size();
                label = "Alle";
                break;
            case "today":
                amount = lectureBean.searchToday(course).size();
                label = "Heute";
                break;
            case "before":
                amount = lectureBean.searchBefore(course).size();
                label = "Anstehend";
                break;
            case "after":
                amount = lectureBean.searchAfter(course).size();
                label = "Vergangen";
                break;
        }
        String href = "/app/lectures/list/";

        if (course != null) {
            href = WebUtils.addQueryParameter(href, "search_course", "" + course.getId());
        }

        if (status != null) {
            href = WebUtils.addQueryParameter(href, "search_status", status);
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
