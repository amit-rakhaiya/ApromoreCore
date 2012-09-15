package org.apromore.dao.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Stores the process in apromore.
 *
 * @author <a href="mailto:cam.james@gmail.com">Cameron James</a>
 */
@Entity
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"})
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Configurable("user")
public class User implements Serializable {

    /**
     * Hard coded for interoperability.
     */
    private static final long serialVersionUID = -2353314404638485548L;

    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String passwd;

    private Set<Process> processes = new HashSet<Process>(0);
    private Set<EditSession> editSessions = new HashSet<EditSession>(0);
    private Set<SearchHistory> searchHistories = new HashSet<SearchHistory>(0);


    /**
     * Default Constructor.
     */
    public User() { }


    /**
     * returns the Id of this Object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the Id of this Object
     * @param id the new Id.
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Get the Primary Key for the Object.
     * @return Returns the Id.
     */
    @Column(name = "username", unique = true, nullable = false, length = 10)
    public String getUsername() {
        return username;
    }

    /**
     * Set the Primary Key for the Object.
     *
     * @param newUsername The id to set.
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }


    /**
     * Get the firstname for the Object.
     * @return Returns the firstname.
     */
    @Column(name = "firstname", unique = false, nullable = true, length = 40)
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the firstname for the Object.
     * @param newFirstname The firstname to set.
     */
    public void setFirstname(final String newFirstname) {
        this.firstname = newFirstname;
    }

    /**
     * Get the lastname for the Object.
     * @return Returns the lastname.
     */
    @Column(name = "lastname", unique = false, nullable = true, length = 40)
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the lastname for the Object.
     * @param newLastname The lastname to set.
     */
    public void setLastname(final String newLastname) {
        this.lastname = newLastname;
    }

    /**
     * Get the email for the Object.
     * @return Returns the email.
     */
    @Column(name = "email", unique = true, nullable = true, length = 80)
    public String getEmail() {
        return email;
    }

    /**
     * Set the Email for the Object.
     * @param newEmail The Email to set.
     */
    public void setEmail(final String newEmail) {
        this.email = newEmail;
    }

    /**
     * Get the password for the Object.
     * @return Returns the password.
     */
    @Column(name = "passwd", unique = false, nullable = false, length = 80)
    public String getPasswd() {
        return passwd;
    }

    /**
     * Set the password for the Object.
     * @param newPassword The password to set.
     */
    public void setPasswd(final String newPassword) {
        this.passwd = newPassword;
    }


    /**
     * Get the processes for the Object.
     * @return Returns the processes.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Process> getProcesses() {
        return this.processes;
    }

    /**
     * Set the processes for the Object.
     * @param newProcesses The processes to set.
     */
    public void setProcesses(final Set<Process> newProcesses) {
        this.processes = newProcesses;
    }

    /**
     * Get the editSessions for the Object.
     * @return Returns the editSessions.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<EditSession> getEditSessions() {
        return this.editSessions;
    }

    /**
     * Set the editSessions for the Object.
     * @param newEditSessions The editSessions to set.
     */
    public void setEditSessions(final Set<EditSession> newEditSessions) {
        this.editSessions = newEditSessions;
    }

    /**
     * Get the searchHistories for the Object.
     * @return Returns the searchHistories.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<SearchHistory> getSearchHistories() {
        return this.searchHistories;
    }

    /**
     * Set the searchHistories for the Object.
     * @param newSearchHistories The searchHistories to set.
     */
    public void setSearchHistories(final Set<SearchHistory> newSearchHistories) {
        this.searchHistories = newSearchHistories;
    }
}
