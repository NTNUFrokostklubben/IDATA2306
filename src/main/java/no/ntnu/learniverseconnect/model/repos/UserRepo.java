package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import java.util.Optional;
import no.ntnu.learniverseconnect.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  User getById(Long id);

  User getUsersById(Long id);

  //User getUsersByName(String name);

  Optional<User> findUserByName(String name);

  Long findIdByName(String name);
}
