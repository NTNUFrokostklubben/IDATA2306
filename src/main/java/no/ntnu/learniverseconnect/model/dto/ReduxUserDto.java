package no.ntnu.learniverseconnect.model.dto;

/**
 * ReduxUserDto is a Data Transfer Object (DTO) that represents a user.
 * Contains fields for the user's id and email.
 */
public class ReduxUserDto {

  private final String email;
    private final long id;

  /**
   * Default constructor for UserDto.
   *
   * @param email name of the user
   * @param id id of the user
   */
  public ReduxUserDto(String email, long id) {
    this.email = email;
    this.id = id;

  }

  public String getEmail() {
    return this.email;
  }

    public long getId() {
        return this.id;
    }

}
