package no.ntnu.learniverseconnect.model.dto;

/**
 * ReduxUserDto is a Data Transfer Object (DTO) that represents a user.
 * Contains fields for the user's id and email.
 */
public class ReduxUserDto {

  private final String email;
    private final long id;
    private final String profilePicture;

  /**
   * Default constructor for UserDto.
   *
   * @param email name of the user
   * @param id id of the user
   */
  public ReduxUserDto(String email, long id, String profilePicture) {
    this.email = email;
    this.id = id;
    this.profilePicture = profilePicture;

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

}
