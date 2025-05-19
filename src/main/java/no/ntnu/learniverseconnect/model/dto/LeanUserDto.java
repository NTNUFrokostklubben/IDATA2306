package no.ntnu.learniverseconnect.model.dto;

/**
 * ReduxUserDto is a Data Transfer Object (DTO) that represents a user.
 * Contains fields for the user's id and email.
 */
public class LeanUserDto {

  private final String email;
  private final long id;
  private final String profilePicture;
  private final String name;

  /**
   * Default constructor for UserDto.
   *
   * @param email name of the user
   * @param id    id of the user
   */
  public LeanUserDto(String email, long id, String profilePicture, String name) {
    this.email = email;
    this.id = id;
    this.profilePicture = profilePicture;
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getProfilePicture() {
    return this.profilePicture;
  }

  public long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

}
