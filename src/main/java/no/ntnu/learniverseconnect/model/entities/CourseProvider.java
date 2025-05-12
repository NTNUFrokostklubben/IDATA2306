package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * Represents a course provider in the system.
 */
@Schema(description = "Organization offering courses in the system")
@Entity
public class CourseProvider implements Serializable {
  @Schema(description = "Unique provider ID", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "Provider name", example = "NTNU")
  private String name;

  @Schema(description = "Hashed password",
      accessMode = Schema.AccessMode.READ_ONLY)
  private String passwordHash;

  @Schema(description = "URL to primary logo",
      example = "http://example.com/logo.png")
  private String logoLink;

  @Schema(description = "URL to alternative logo",
      example = "http://example.com/alt-logo.png")
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
   *
   * @return the provider logo link
   */
  public String getLogoLink() {
    return logoLink;
  }

  /**
   * Sets the link to the provider's logo.
   *
   * @param providerLogoLink the provider logo link to set
   */
  public void setLogoLink(String providerLogoLink) {
    this.logoLink = providerLogoLink;
  }

  /**
   * Gets the link to the provider's alternative logo.
   *
   * @return the provider alt logo link
   */
  public String getAltLogoLink() {
    return altLogoLink;
  }

  /**
   * Sets the link to the provider's alternative logo.
   *
   * @param providerAltLogoLink the provider alt logo link to set
   */
  public void setAltLogoLink(String providerAltLogoLink) {
    this.altLogoLink = providerAltLogoLink;
  }
}
