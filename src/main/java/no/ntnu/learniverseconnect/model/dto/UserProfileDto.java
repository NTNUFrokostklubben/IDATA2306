package no.ntnu.learniverseconnect.model.dto;

/**
 * UserProfileDto is a Data Transfer Object (DTO) that represents the user profile
 * information.
 * It contains the profile picture URL of the user.
 */
//TODO delete this class if not needed
public class UserProfileDto {
  private String profilePicture;

  public UserProfileDto(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getProfilePicture() {
    return this.profilePicture;
  }
}
