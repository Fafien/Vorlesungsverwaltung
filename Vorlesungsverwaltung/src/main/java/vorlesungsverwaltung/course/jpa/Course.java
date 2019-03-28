/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vorlesungsverwaltung.course.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import vorlesungsverwaltung.common.jpa.User;

/**
 *
 * @author jonas
 */
@Entity
@Table(name = "VORLESUNGSVERWALTUNG_COURSE")
public class Course implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String kursbezeichnung;

    @ManyToOne
    private List<User> user;

    @OneToMany
    private List<Lecture> lecture;

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKursbezeichnung() {
        return kursbezeichnung;
    }

    public void setKursbezeichnung(String kursbezeichnung) {
        this.kursbezeichnung = kursbezeichnung;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Lecture> getLecture() {
        return lecture;
    }

    public void setLecture(List<Lecture> lecture) {
        this.lecture = lecture;
    }

    public void addLecture(Lecture lecture) {
        this.lecture.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        this.lecture.remove(lecture);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Equals and hashCode">
    @Override
    public int hashCode() {
        int hash = 5;
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
        final Course other = (Course) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.kursbezeichnung, other.kursbezeichnung)) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
