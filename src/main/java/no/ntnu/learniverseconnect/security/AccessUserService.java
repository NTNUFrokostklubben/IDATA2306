package no.ntnu.learniverseconnect.security;

import java.util.Optional;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccessUserService implements UserDetailsService {
  @Autowired
  UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepo.findUserByName(username);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User " + username + " not found");
    }
  }
}
