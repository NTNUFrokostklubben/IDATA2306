package no.ntnu.learniverseconnect.model.dto;

/**
 * UserDto is a Data Transfer Object (DTO) that represents a user.
 * Contains fields for the user's name, password hash, and email.
 */
public class AuthenticationUserDto {

  private final String name;
  private final String passwordHash;
  private final String email;

  /**
   * Default constructor for UserDto.
   *
   * @param name name of the user
   * @param passwordHash hashed password of the user
   * @param email email of the user
   */
  public AuthenticationUserDto(String name, String passwordHash, String email) {
    this.name = name;
    this.passwordHash = passwordHash;
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public String getEmail() {
    return this.email;
  }
}
