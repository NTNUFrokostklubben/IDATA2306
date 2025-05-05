package no.ntnu.learniverseconnect.security;

import java.io.IOException;
import java.util.Optional;
import no.ntnu.learniverseconnect.model.dto.UserProfileDto;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.RoleRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccessUserService implements UserDetailsService {
  private int MIN_PASSWORD_LENGTH = 8;
  @Autowired
  UserRepo userRepo;

  @Autowired
  RoleRepo roleRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepo.findUserByEmail(email);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User with email: " + email + " not found");
    }
  }

  private boolean userExists(String email){
    try{
      loadUserByUsername(email);
      return true;
    } catch (UsernameNotFoundException ex){
      return false;
    }
  }

  public void tryCreateNewUser(String username, String password, String email) throws IOException{
    String errormessage;
    String emailCheck = checkEmailRequirements(email);
    if(emailCheck != null){
      errormessage = emailCheck;
    } else if (username == null || username.isEmpty()){
      errormessage = "Username cannot be empty";
    } else{
      errormessage = checkPasswordRequirements(password);
      if (errormessage == null){
        createUser(username, password, email);
      }
    }
    if (errormessage != null){
      throw new IOException(errormessage);
    }
  }

  public String checkPasswordRequirements(String password){
    String errormessage = null;
    if (password == null || password.isEmpty()){
      errormessage = "Password cannot be empty";
    } else if (password.length() < MIN_PASSWORD_LENGTH){
      errormessage = "Password must be at least "+ MIN_PASSWORD_LENGTH +" characters";
    }
    return errormessage;
  }

  public String checkEmailRequirements(String email){
    String errormessage = null;
    if (email == null || email.isEmpty()){
      errormessage = "Email cannot be empty";
    } else if (!email.contains("@")){
      errormessage = "Email must contain @";
    } else if (!email.contains(".")){
      errormessage = "Email must contain .";
    } else if (userRepo.findUserByEmail(email).isPresent()) {
      errormessage = "Email already exists";
    }
    return errormessage;
  }


  private void createUser(String username, String password, String email){
    Role role = roleRepo.findOneByName("ROLE_USER");
    if (role != null){
      User user = new User(username, createHash(password), email);
      user.addRole(role);
      userRepo.save(user);
    }
  }

  private String createHash(String password){
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean updateProfile(User user, UserProfileDto profileDFO){
    user.setProfilePicture(profileDFO.getProfilePicture());
    userRepo.save(user);
    return true;
  }

}
