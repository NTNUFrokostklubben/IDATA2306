package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class CourseProvider implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private String passwordHash;
  private String logoLink;
  private String altLogoLink;

  /**
   * Gets the unique ID of the course provider.
   *
   * @return the course provider ID
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the name of the course provider.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the course provider.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the hashed password of the course provider.
   *
   * @return the password hash
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Sets the hashed password of the course provider.
   *
   * @param passwordHash the password hash to set
   */
  public void setPasswordHash(String passwordHash) {
    if (passwordHash == null || passwordHash.isEmpty()) {
      throw new IllegalArgumentException("password cannot be null or empty");
    }
    this.passwordHash = passwordHash;
  }

  /**
   * Gets the link to the provider's logo.
   * @return the provider logo link
   */
  public String getLogoLink() {
    return logoLink;
  }

    /**
     * Sets the link to the provider's logo.
     * @param providerLogoLink the provider logo link to set
     */
  public void setLogoLink(String providerLogoLink) {
    this.logoLink = providerLogoLink;
  }

    /**
     * Gets the link to the provider's alternative logo.
     * @return the provider alt logo link
     */
  public String getAltLogoLink() {
    return altLogoLink;
  }

    /**
     * Sets the link to the provider's alternative logo.
     * @param providerAltLogoLink the provider alt logo link to set
     */
  public void setAltLogoLink(String providerAltLogoLink) {
    this.altLogoLink = providerAltLogoLink;
  }
}
