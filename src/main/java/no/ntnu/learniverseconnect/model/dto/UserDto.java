package no.ntnu.learniverseconnect.model.dto;


public class UserDto {

  private final String name;
  private final String passwordHash;
  private final String email;

  public UserDto(String name, String passwordHash, String email){
    this.name = name;
    this.passwordHash = passwordHash;
    this.email = email;
  }

  public String getName(){
    return this.name;
  }

  public String getPasswordHash(){
    return this.passwordHash;
  }

  public String getEmail(){
    return this.email;
  }
}
