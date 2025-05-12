package no.ntnu.learniverseconnect.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a role in the system with a unique id.
 */
@Schema(description = "Represents a user role in the system")
@Entity(name = "roles")
public class Role {
  @Schema(description = "Unique identifier of the role", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "Name of the role", example = "ROLE_ADMIN")
  private String name;

  @Schema(description = "Users assigned to this role",
      accessMode = Schema.AccessMode.READ_ONLY)
  @ManyToMany(mappedBy = "roles")
  @JsonBackReference
  private Set<User> users = new LinkedHashSet<>();

  /**
   * Empty constructor.
   */
  public Role() {
    // Empty
  }

  /**
   * Constructor with name.
   *
   * @param name the name of the role
   */
  public Role(String name) {
    this.name = name;
  }

  /**
   * Gets the id of the role.
   *
   * @return the id for the role
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the role.
   *
   * @param id the id for the role
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the users associated with this role.
   *
   * @return the users associated with this role
   */
  public Set<User> getUsers() {
    return users;
  }

  /**
   * Sets the users associated with this role.
   *
   * @param users the users to set
   */
  public void setUsers(Set<User> users) {
    this.users = users;
  }

  /**
   * Gets the name of the role.
   *
   * @return the name of the role
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the role.
   *
   * @param name the name of the role
   */
  public void setName(String name) {
    this.name = name;
  }

}
