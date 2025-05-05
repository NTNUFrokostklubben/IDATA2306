package no.ntnu.learniverseconnect.model.dto;

public class UserProfileDto {
  private String email;
  private String profilePicture;

  public UserProfileDto(String email, String profilePicture){
    this.email = email;
    this.profilePicture = profilePicture;
  }

  public String getEmail(){
    return this.email;
  }

  public String getProfilePicture(){
    return this.profilePicture;
  }
}
