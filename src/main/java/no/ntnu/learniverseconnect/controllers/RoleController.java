package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.repos.RoleRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling role-related requests.
 */
@RestController
public class RoleController {

  private RoleRepo roleRepo;
  private final Logger logger = Logger.getLogger(RoleController.class.getName());

  /**
   * Constructor for RoleController.

   * @param roleRepo Repository for roles
   */
  public RoleController(RoleRepo roleRepo) {
    this.roleRepo = roleRepo;
  }

  /**
   * Get all roles.
   *
   * @return a list of roles
   */
  @GetMapping("/roles")
  public ResponseEntity<List<Role>> getRoles() {
    List<Role> roles = roleRepo.findAll();
    if (roles.isEmpty()) {
      logger.warning("No roles found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching all roles");
      return ResponseEntity.status(200).body(roles);
    }
  }

}
