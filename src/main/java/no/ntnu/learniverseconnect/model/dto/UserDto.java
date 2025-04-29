package no.ntnu.learniverseconnect.model.dto;


public class UserDto {

  private final String name;
  private final String passwordHash;

  public UserDto(String name, String passwordHash){
    this.name = name;
    this.passwordHash = passwordHash;
  }

  public String getName(){
    return name;
  }

  public String getPasswordHash(){
    return passwordHash;
  }
}
