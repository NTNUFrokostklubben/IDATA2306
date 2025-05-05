package no.ntnu.learniverseconnect.model.dto;

public class UserProfileDto {
  private String profilePicture;

  public UserProfileDto( String profilePicture){
    this.profilePicture = profilePicture;
  }

  public String getProfilePicture(){
    return this.profilePicture;
  }
}
