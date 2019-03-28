package vorlesungsverwaltung.common.jpa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import vorlesungsverwaltung.course.jpa.Course;

@Entity
@Table(name = "VORLESUNGSVERWALTUNG_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "uniqueNumber")
    private Long uniqueNumber;

    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Ihr gewünschter Benutzername darf nur zwischen 5 und 64 Zeichen lang sein")
    @NotNull(message = "Bitte geben Sie einen Benutzernamen ein!")
    private String username = "";

    @NotNull(message = "Bitte geben Sie ihren Vornamen ein!")
    private String firstName = "";

    @NotNull(message = "Bitte geben Sie ihren Nachnamen ein!")
    private String lastName = "";

    //Length 64 reuqired becuase of sha-256
    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Bitte geben Sie ein Passwort ein!")
    private String password = "";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    private List<String> groups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Course course;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public User() {
    }

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniqueNumber = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + (10000 + new Random().nextInt(90000))); //aktuelles Datum + Zeit + 5 stellige Random Zahl
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Long uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Equals and hashCode">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.uniqueNumber);
        hash = 71 * hash + Objects.hashCode(this.username);
        hash = 71 * hash + Objects.hashCode(this.password);
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
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.uniqueNumber, other.uniqueNumber)) {
            return false;
        }
        return true;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Weitere Methoden">
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    //</editor-fold>
}
