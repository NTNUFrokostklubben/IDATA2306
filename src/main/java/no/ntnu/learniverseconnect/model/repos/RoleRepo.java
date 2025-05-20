package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The role repository.
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
  Role findOneByName(String name);
}
