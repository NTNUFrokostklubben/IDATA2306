package no.ntnu.learniverseconnect.model.dto;

/**
 * AuthenticationRequest is a DTO (Data Transfer Object) that represents the
 * authentication request payload.
 * It contains the email and password fields required for user authentication.
 */
public class AuthenticationRequest {
  private String email;
  private String password;

  /**
   * Default constructor for AuthenticationRequest.
   */
  public AuthenticationRequest() {

  }

  /**
   * Constructor for AuthenticationRequest with email and password.
   *
   * @param email    the email of the user
   * @param password the password of the user
   */
  public AuthenticationRequest(String email, String password) {
    setEmail(email);
    setPassword(password);
  }

  /**
   * Gets the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email of the user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the password of the user
   */
  public void setPassword(String password) {
    this.password = password;
  }


}
