package no.ntnu.learniverseconnect.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
import jakarta.persistence.Transient;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;


/**
 * Represents a user in the system with a unique id.
 */
@Entity
public class User {

  @Schema(description = "Unique ID of the user", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "Full name of the user", example = "John Doe")
  private String name;

  @Schema(description = "Email address (unique)", example = "user@example.com")
  private String email;

  @Schema(description = "Hashed password (never exposed in API responses)", accessMode = Schema.AccessMode.READ_ONLY)
  private String passwordHash;

  @Schema(description = "Account active status", example = "true")
  private boolean active = true;

  @Schema(description = "Roles assigned to the user")
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<>();

  @Schema(description = "URL to the user's profile picture", example = "http://example.com/image.jpg")

  private String profilePicture;


  @Schema(description = "Timestamp of account creation", example = "2023-01-01T12:00:00Z")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp userCreated;

  public User() {
  }

  /**
   * Constructor for creating a new user with the specified name, password, and email.
   */
  public User(String name, String password, String email) {
    this.name = name;
    this.passwordHash = password;
    this.email = email;
  }
  /**
   * Constructor for creating a new user with the specified name, password, email,
   * and profile picture.
   */
  public User(String name, String password, String email, String profilePicture) {
    this.name = name;
    this.passwordHash = password;
    this.email = email;
    this.profilePicture = profilePicture;
  }

  /**
   * Sets the current timestamp before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.userCreated = new Timestamp(System.currentTimeMillis());

  }

  /**
   * Gets the id of the user.
   *
   * @return the id of the user
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the timestamp of when the user was created.
   */
  public Timestamp getUserCreated() {
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
   * Checks if the user is active.
   *
   * @return true if the user is active, false otherwise
   */
  public boolean isActive() {
    return active;
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
   * Add a role to the user.
   *
   * @param role Role to add
   */
  public void addRole(Role role) {
    this.roles.add(role);
  }

  /**
   * Check if this user is an admin.
   *
   * @return True if the user has admin role, false otherwise
   */
  public boolean isAdmin() {
    return this.hasRole("ROLE_ADMIN");
  }

  /**
   * Check if the user has a specified role.
   *
   * @param roleName Name of the role
   * @return True if hte user has the role, false otherwise.
   */
  public boolean hasRole(String roleName) {
    boolean found = false;
    Iterator<Role> it = roles.iterator();
    while (!found && it.hasNext()) {
      Role role = it.next();
      if (role.getName().equals(roleName)) {
        found = true;
      }
    }
    return found;
  }

}
