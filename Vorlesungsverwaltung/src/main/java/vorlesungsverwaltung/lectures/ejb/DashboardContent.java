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

import vorlesungsverwaltung.common.web.WebUtils;
import vorlesungsverwaltung.dashboard.ejb.DashboardContentProvider;
import vorlesungsverwaltung.dashboard.ejb.DashboardSection;
import vorlesungsverwaltung.dashboard.ejb.DashboardTile;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vorlesungsverwaltung.lectures.jpa.Course;

/**
 * EJB zur Definition der Dashboard-Kacheln für Aufgaben.
 */
@Stateless(name = "lectures")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    private CourseBean courseBean;

    @EJB
    private LectureBean lectureBean;

    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        DashboardSection section = this.createSection(null);
        sections.add(section);

        List<Course> courses = this.courseBean.findAll();

        for (Course course : courses) {
            section = this.createSection(course);
            sections.add(section);
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
            section.setLabel("Alle Kategorien");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(course, "Alle", "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for(int i = 0; i < 4; i++){
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
                case 3:
                    icon = "cancel";
                    status = "deleted";
                    break;
            }
            
            String cssClass1 = cssClass + " status-" + status;
            
            tile = this.createTile(course, status, status, cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param category
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(Course course, String status, String label, String cssClass, String icon) {
        int amount = 0;
        switch(status){
            case "today":
                amount = lectureBean.searchToday(course).size();
                break;
            case "before":
                amount = lectureBean.searchBefore(course).size();
                break;
            case "after":
                amount = lectureBean.searchAfter(course).size();
                break;
            case "deleted":
                amount = lectureBean.searchDeleted(course).size();
                break;
        }
        String href = "/app/tasks/list/";

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
