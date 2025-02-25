package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
}
