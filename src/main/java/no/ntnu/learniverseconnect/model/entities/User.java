package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a user in the system with a unique id.
 */
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String passwordHash;
  private boolean active = true;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<>();

  private String profilePicture;
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp userCreated;


  /**
   * Sets the current timestamp before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.userCreated = new Timestamp(System.currentTimeMillis());
  }

  /**
   * Gets the id of the user
   *
   * @return the id of the user
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the timestamp of when the user was created
   */
  public Timestamp getUserCreated(){
    return this.userCreated;
  }

  /**
   * Gets the name of the user.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the user.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the hashed password of the user.
   *
   * @return the password hash
   */
  public String getPasswordHash() {
    return this.passwordHash;
  }

  /**
   * Sets the hashed password of the user.
   *
   * @param passwordHash the password hash to set
   */
  public void setPasswordHash(String passwordHash) {
    if (passwordHash == null || passwordHash.isEmpty()) {
      throw new IllegalArgumentException("password hash cannot be null or empty");
    }
    this.passwordHash = passwordHash;
  }

  /**
   * Gets the role of the user.
   *
   * @return the role
   */
  public Set<Role> getRole() {
    return roles;
  }

  /**
   * Sets the role of the user.
   *
   * @param roles the role to set
   */
  public void setRole(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Gets the profilePicture URL of the user.
   *
   * @return the URL to the profile picture
   */
  public String getProfilePicture() {
    return profilePicture;
  }

  /**
   * Sets the profilePicture URL  of the user.
   *
   * @param profilePicture the URL to set
   */
  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  /**
   * Changes the user status.
   *
   * @param active the status to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Checks if the user is active.
   *
   * @return true if the user is active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Add a role to the user.
   *
   * @param role Role to add
   */
  public void addRole(Role role) {
    this.roles.add(role);
  }
}
