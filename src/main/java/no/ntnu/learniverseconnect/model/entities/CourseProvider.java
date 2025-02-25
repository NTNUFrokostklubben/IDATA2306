package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class CourseProvider implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private String passwordHash;

  /**
   * Gets the unique ID of the course provider.
   * @return the course provider ID
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the name of the course provider.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the course provider.
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the hashed password of the course provider.
   * @return the password hash
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Sets the hashed password of the course provider.
   * @param passwordHash the password hash to set
   */
  public void setPasswordHash(String passwordHash) {
    if (passwordHash == null || passwordHash.isEmpty()){
      throw new IllegalArgumentException("password cannot be null or empty");
    }
    this.passwordHash = passwordHash;
  }

}
