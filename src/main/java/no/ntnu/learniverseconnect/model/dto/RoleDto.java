package no.ntnu.learniverseconnect.model.dto;


/**
 * Data Transfer Object (DTO) representing a role in the Learniverse Connect system.
 * This class is used to transfer role-related information between different layers of the application.
 */
public class RoleDto {
  private Long id;
  private String name;

  /**
   * Constructs a new RoleDto with the specified ID and name.
   *
   * @param id   The unique identifier for the role.
   * @param name The name of the role.
   */
  public RoleDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Gets the unique identifier of the role.
   *
   * @return The role's ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the role.
   *
   * @param id The role's ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the role.
   *
   * @return The role's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the role.
   *
   * @param name The role's name to set.
   */
  public void setName(String name) {
    this.name = name;
  }
}