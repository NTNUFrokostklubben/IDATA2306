package no.ntnu.learniverseconnect.model.repos;

import java.util.Optional;
import no.ntnu.learniverseconnect.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The user repository.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  User getById(Long id);

  User getUsersById(Long id);

  //User getUsersByName(String name);

  Optional<User> findUserByName(String name);

  Long findIdByName(String name);

  Optional<User> findUserByEmail(String email);

  User getUsersByEmail(String email);

  void deleteUserById(Long id);

  boolean existsUserById(Long id);
}
