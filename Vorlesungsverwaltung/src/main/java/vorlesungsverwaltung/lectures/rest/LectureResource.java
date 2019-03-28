/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package vorlesungsverwaltung.lectures.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import vorlesungsverwaltung.lectures.ejb.LectureBean;
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

    //TODO: Methoden des Webservices hinzufügen:
    /*Anforderungen an den Webservice sind:
    * Ändernde Zugriffe sind nicht zwingend notwendig, allerdings
    * verschiedene Suchfunktionen:
    *
    * GET Methode für Vorlesungen (alle, Filter über Name oder ID, Filter per Kurs)
    * GET Methode für Kurse (alle, Filter über Name)
    *
     */
    @GET
    public List<Lecture> findLectures() {
        return this.lectureBean.findAll();
    }
}
