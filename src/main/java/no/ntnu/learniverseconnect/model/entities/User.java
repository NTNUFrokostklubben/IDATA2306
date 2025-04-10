package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
  private String role;
  private String profilePicture;
  //TODO: Change to enum


  public Long getId() {
    return id;
  }

  /**
   * Gets the name of the user.
   *
   * @return the name
   */
  public String getName() {
    return name;
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
    return email;
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
    return passwordHash;
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
  public String getRole() {
    return role;
  }

  /**
   * Sets the role of the user.
   *
   * @param role the role to set
   */
  public void setRole(String role) {
    this.role = role;
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
}
