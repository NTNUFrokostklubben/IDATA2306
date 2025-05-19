package no.ntnu.learniverseconnect.model.dto;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Data Transfer Object (DTO) representing a rich user profile in the Learniverse Connect system.
 * This class contains detailed user information, including roles, for transfer between application layers.
 */
public class RichUserDto {
  private Long id;
  private String name;
  private String email;
  private boolean active;
  private String profilePicture;
  private Timestamp userCreated;
  private Set<RoleDto> role;

  /**
   * Constructs a new richUserDto with the specified user details.
   *
   * @param id             The unique identifier for the user.
   * @param name           The name of the user.
   * @param email          The email address of the user.
   * @param active         The active status of the user.
   * @param profilePicture The URL or path to the user's profile picture.
   * @param userCreated    The timestamp when the user was created.
   * @param roles          The set of roles assigned to the user.
   */
  public RichUserDto(Long id, String name, String email, boolean active, String profilePicture, Timestamp userCreated, Set<RoleDto> roles) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.active = active;
    this.profilePicture = profilePicture;
    this.userCreated = userCreated;
    this.role = roles;
  }

  /**
   * Gets the unique identifier of the user.
   *
   * @return The user's ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the user.
   *
   * @param id The user's ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the user.
   *
   * @return The user's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the user.
   *
   * @param name The user's name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the email address of the user.
   *
   * @return The user's email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address of the user.
   *
   * @param email The user's email to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Checks if the user is active.
   *
   * @return True if the user is active, false otherwise.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the active status of the user.
   *
   * @param active The active status to set.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets the profile picture URL or path of the user.
   *
   * @return The user's profile picture.
   */
  public String getProfilePicture() {
    return profilePicture;
  }

  /**
   * Sets the profile picture URL or path of the user.
   *
   * @param profilePicture The profile picture to set.
   */
  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  /**
   * Gets the timestamp when the user was created.
   *
   * @return The user creation timestamp.
   */
  public Timestamp getUserCreated() {
    return userCreated;
  }

  /**
   * Sets the timestamp when the user was created.
   *
   * @param userCreated The creation timestamp to set.
   */
  public void setUserCreated(Timestamp userCreated) {
    this.userCreated = userCreated;
  }

  /**
   * Gets the set of roles assigned to the user.
   *
   * @return The user's roles.
   */
  public Set<RoleDto> getRole() {
    return role;
  }

  /**
   * Sets the set of roles assigned to the user.
   *
   * @param role The roles to set.
   */
  public void setRole(Set<RoleDto> role) {
    this.role = role;
  }
}