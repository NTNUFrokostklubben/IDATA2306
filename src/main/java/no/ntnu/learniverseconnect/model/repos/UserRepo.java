package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
