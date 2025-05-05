package no.ntnu.learniverseconnect.model.dto;


public class AuthenticationRequest {
  private String email;
  private String password;

  public AuthenticationRequest(){

  }

  public AuthenticationRequest(String email, String password){
    setEmail(email);
    setPassword(password);
  }

  public void setEmail(String email){
    this.email = email;
  }
 
  public String getEmail(){
    return this.email;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getPassword(){
    return this.password;
  }


}
