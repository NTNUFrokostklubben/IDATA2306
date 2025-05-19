package no.ntnu.learniverseconnect.security;

import java.io.IOException;
import java.util.Optional;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.RoleRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service that handles user authentication and registration.
 */
@Service
public class AccessUserService implements UserDetailsService {
  @Autowired
  UserRepo userRepo;
  @Autowired
  RoleRepo roleRepo;
  private int MIN_PASSWORD_LENGTH = 8;

  @Value("${app.uploadBaseUrl}")
  private String baseUrl;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepo.findUserByEmail(email);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User with email: " + email + " not found");
    }
  }

  /**
   * Try to create a new user with the given username, password and email.
   *
   * @param username the username of the new user
   * @param password the password of the new user
   * @param email    the email of the new user
   * @throws IOException if the user could not be created
   */
  public void tryCreateNewUser(String username, String password, String email) throws IOException {
    String errormessage;
    String emailCheck = checkEmailRequirements(email);
    if (emailCheck != null) {
      errormessage = emailCheck;
    } else if (username == null || username.isEmpty()) {
      errormessage = "Username cannot be empty";
    } else {
      errormessage = checkPasswordRequirements(password);
      if (errormessage == null) {
        createUser(username, password, email);
      }
    }
    if (errormessage != null) {
      throw new IOException(errormessage);
    }
  }

  /**
   * Try to create a new user using OAuth2.
   *
   * @param username the username of the new user
   * @param email    the email of the new user
   * @throws IOException if the user could not be created
   */
  public void tryCreateNewOauthUser(String username, String email) throws IOException {
    String errormessage = null;
    String emailCheck = checkEmailRequirements(email);
    if (emailCheck != null) {
      errormessage = emailCheck;
    } else if (username == null || username.isEmpty()) {
      errormessage = "Username cannot be empty";
    } else {
      createOauthUser(username, email);
    }
    if (errormessage != null) {
      throw new IOException(errormessage);
    }
  }

  /**
   * Check if the password meets the requirements.
   *
   * @param password the password to check
   * @return null if the password meets the requirements, otherwise an error message
   */
  public String checkPasswordRequirements(String password) {
    String errormessage = null;
    if (password == null || password.isEmpty()) {
      errormessage = "Password cannot be empty";
    } else if (password.length() < MIN_PASSWORD_LENGTH) {
      errormessage = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
    }
    return errormessage;
  }

  /**
   * Check if the email meets the requirements.
   *
   * @param email the email to check
   * @return null if the email meets the requirements, otherwise an error message
   */
  public String checkEmailRequirements(String email) {
    String errormessage = null;
    if (email == null || email.isEmpty()) {
      errormessage = "Email cannot be empty";
    } else if (!email.contains("@")) {
      errormessage = "Email must contain @";
    } else if (!email.contains(".")) {
      errormessage = "Email must contain .";
    } else if (userRepo.findUserByEmail(email).isPresent()) {
      errormessage = "Email already exists";
    }
    return errormessage;
  }


  /**
   * Create a new user with the given username, password and email.
   *
   * @param username the username of the new user
   * @param password the password of the new user
   * @param email    the email of the new user
   */
  private void createUser(String username, String password, String email) {
    Role role = roleRepo.findOneByName("ROLE_USER");
    String profilePicture = baseUrl + "default_img.png";
    if (role != null) {
      User user = new User(username, createHash(password), email, profilePicture);
      user.addRole(role);
      userRepo.save(user);
    }
  }

  /**
   * Create a new user with the given username and email using OAuth2.
   *
   * @param username username of the new user
   * @param email    email of the new user
   */
  private void createOauthUser(String username, String email) {
    Role role = roleRepo.findOneByName("ROLE_USER");
    String profilePicture = baseUrl + "default_img.png";
    if (role != null) {
      User user = new User(username, null, email, profilePicture);
      user.addRole(role);
      userRepo.save(user);
    }
  }

  /**
   * Load or create a new user with the given email and name using OAuth2.
   *
   * @param email email of the user, taken from the OAuth2 provider
   * @param name name of the user, taken from the OAuth2 provider
   * @return the user details of the user
   * @throws IOException if the user could not be created
   */
  public UserDetails loadOrCreateGoogleUser(String email, String name) throws IOException {
    // Check if user exists
    try {
      return loadUserByUsername(email);
    } catch (Exception e) {
      tryCreateNewOauthUser(name, email);
      return loadUserByUsername(email);
    }
  }

  /**
   * Create a hash of the given password.
   *
   * @param password the password to hash
   * @return the hash of the password
   */
  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

}
