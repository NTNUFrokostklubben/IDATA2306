package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.logging.Logger;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.repos.RoleRepo;
import no.ntnu.learniverseconnect.security.swagger.SecuredEndpoint;
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
  @Operation(
      summary = "Get all roles",
      description = "Returns a list of all roles"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "List of all roles"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No roles found"
      )
  })
  @SecuredEndpoint
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
