package no.ntnu.learniverseconnect.model.dto;

/**
 * The authentication response
 */
public class AuthenticationResponse {
  private final String jwt;

  /**
   * The constructor for the authentication response
   *
   * @param jwt the jwt token
   */
  public AuthenticationResponse(String jwt){
    this.jwt =jwt;
  }

  /**
   * Returns the JWT
   *
   * @return the JWT
   */
  public String getJwt(){
    return this.jwt;
  }
}
