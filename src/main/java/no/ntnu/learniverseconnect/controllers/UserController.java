package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import no.ntnu.learniverseconnect.model.dto.ReduxUserDto;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import no.ntnu.learniverseconnect.security.SecuredEndpoint;
import no.ntnu.learniverseconnect.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController class is responsible for handling HTTP requests related to user operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "User Management", description = "APIs for managing user accounts and profiles")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  UserRepo repo;

  @Autowired
  public UserController(UserRepo repo) {
    this.repo = repo;
  }


  /**
   * Get a user by id.
   *
   * @return the user with the given id
   */
  @Operation(summary = "Get user by ID",
      description = "Retrieves a user's full details by their ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @SecuredEndpoint
  @GetMapping("/user/get")
  public ResponseEntity<User> getUserById() {
    long uid = SecurityUtils.getAuthenticatedUserId();
    User user = repo.getUsersById(uid);
    if (user == null) {
      logger.warn("User with id {} not found", uid);
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching user with id: {}", uid);
      return ResponseEntity.status(200).body(user);
    }
  }


  /**
   * Get safe user dto by email.
   *
   * @param email the email of the user
   * @return the user with the given id
   */
  @Operation(summary = "Get safe user DTO by email",
      description = "Retrieves a limited user DTO (for Redux state) by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(schema = @Schema(implementation = ReduxUserDto.class))),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @SecuredEndpoint
  @GetMapping("/userDto/{email}")
  public ResponseEntity<ReduxUserDto> getReduxUserDtoById(@PathVariable String email) {
    User user = repo.getUsersByEmail(email);
    if (user == null) {
      logger.warn("User with email {} not found", email);
      return ResponseEntity.status(404).body(null);
    } else {
      ReduxUserDto userDto = new ReduxUserDto(user.getEmail(),
          user.getId(), user.getProfilePicture(), user.getName());
      logger.info("Fetching user with email: {}", email);
      return ResponseEntity.status(200).body(userDto);
    }
  }


  /**
   * Get the user's profile picture that matches the email.
   *
   * @param email the email of the user to fetch
   * @return the user's profile picture with that email
   */
  @Operation(summary = "Get user's profile picture",
      description = "Retrieves the profile picture URL of a user by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Profile picture found",
          content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "204", description = "No profile picture set"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @Deprecated
  @GetMapping("/userProfilePicture/{email}")
  public ResponseEntity<String> getProfilePicByEmail(@PathVariable String email) {
    Optional<User> userOptional = repo.findUserByEmail(email);
    if (userOptional.isEmpty()) {
      logger.warn("User with email {} not found", email);
      return ResponseEntity.status(404).body(null);
    } else {
      User user = userOptional.get();
      String profilePicture = user.getProfilePicture();
      if (profilePicture == null || profilePicture.isEmpty()) {
        logger.warn("User with email {} has no profile picture", email);
        return ResponseEntity.status(204).body(null);
      } else {
        logger.info("Fetching profile picture for user with email: {}", email);
        return ResponseEntity.status(200).body(user.getProfilePicture());
      }
    }
  }


  /**
   * Get the user that matches the email.
   *
   * @param email the email of the user to fetch
   * @return the user with that email
   */
  @Operation(summary = "Get user by email",
      description = "Retrieves a user's full details by their email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @SecuredEndpoint
  @GetMapping("/UserByEmail/{email}")
  public ResponseEntity<User> getIdByEmail(@PathVariable String email) {
    Optional<User> userOptional = repo.findUserByEmail(email);
    if (userOptional.isEmpty()) {
      logger.warn("User with email {} not found", email);
      return ResponseEntity.status(404).body(null);
    } else {
      User user = userOptional.get();
      logger.info("Fetching user with email: {}", email);
      return ResponseEntity.status(200).body(user);
    }
  }


  /**
   * Get the total count of users.
   *
   * @return the total count of users
   */
  @Operation(summary = "Get total user count",
      description = "Retrieves the total number of registered users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Count retrieved",
          content = @Content(schema = @Schema(implementation = Integer.class))),
      @ApiResponse(responseCode = "404", description = "No users found")
  })
  @SecuredEndpoint
  @GetMapping("/users/total")
  public ResponseEntity<Integer> getUserTotal() {
    List<User> totalUsers = repo.findAll();
    if (totalUsers.isEmpty()) {
      logger.warn("No users found");
      return ResponseEntity.status(404).body(0);
    }
    logger.info("Fetching total number of users");
    return ResponseEntity.status(200).body(totalUsers.size());
  }


    /**
     * Get all users.
     *
     * @return a list of all users
     */
  @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users found",
          content = @Content(schema = @Schema(implementation = User.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No users found")
  })
  @SecuredEndpoint
  @GetMapping("/users")
  public ResponseEntity<Iterable<User>> getAllUsers() {
    logger.info("Fetching all users");
    return ResponseEntity.status(200).body(repo.findAll());
  }



  /**
   * Get the total users that signed up the last 30 days.
   *
   * @return the amount of users for the last 30 days
   */
  @Operation(summary = "Get new user count (30 days)",
      description = "Retrieves the number of users registered in the last 30 days")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Count retrieved",
          content = @Content(schema = @Schema(implementation = Float.class))),
      @ApiResponse(responseCode = "404", description = "No users found")
  })
  @SecuredEndpoint
  @GetMapping("/users/newUsers")
  public ResponseEntity<Float> getNewUsers() {
    List<User> users = repo.findAll();
    if (users.isEmpty()) {
      logger.warn("No users found");
      return ResponseEntity.status(404).body(0f);
    }
    float userSum = 0;
    for (User user : users) {
      if (user.getUserCreated().after(
          Timestamp.valueOf(LocalDateTime.now().minusDays(30)))) {
        userSum++;
      }
    }
    logger.info("Fetching total number of new users in the last 30 days");
    return ResponseEntity.status(200).body(userSum);
  }



  /**
   * Deletes a user from the database.
   *
   * @param id the id of the user to delete
   * @return a response entity with the status of the operation
   */
  @Operation(summary = "Delete a user", description = "Permanently deletes a user account by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @SecuredEndpoint
  @DeleteMapping("/user/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable int id) {
    if (repo.existsById(id)) {
      logger.info("Deleting user with id: {}", id);
      repo.deleteById(id);
      return ResponseEntity.status(200).body("User with id " + id + " deleted");
    } else {
      logger.warn("User with id {} not found", id);
      return ResponseEntity.status(404).body("User with id " + id + " not found");
    }
  }


  /**
   * Updates a user's image link in the backend.
   *
   * @param imageLink the new image link to set
   * @return a response entity with the updated ReduxUserDto or a 404 status if no user found.
   */
  @Operation(summary = "Update user's profile picture",
      description = "Updates the profile picture URL of a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Profile picture updated",
          content = @Content(schema = @Schema(implementation = ReduxUserDto.class))),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "New profile picture URL",
      required = true,
      content = @Content(schema = @Schema(implementation = String.class))
  )
  @SecuredEndpoint
    @PutMapping("/user/image")
    public ResponseEntity<ReduxUserDto> updateUserImage(
        @RequestBody String imageLink) {
    long uid = SecurityUtils.getAuthenticatedUserId();
        Optional<User> userOptional = repo.findById((int) uid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePicture(imageLink.replace("\"" , ""));
            repo.save(user);
            ReduxUserDto userDto = new ReduxUserDto(user.getEmail(), user.getId(),
                user.getProfilePicture(), user.getName());
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } else {
            logger.warn("User with id {} not found", uid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


  /**
   * Updates a user in the database.
   *
   * @param userDto the user object with updated information (profile picture, roles, active)
   * @return a response entity with the status of the operation
   */
  @Operation(summary = "Update user details",
      description = "Updates a user's profile picture, roles, and active status")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated",
          content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "User object with updated information",
      required = true,
      content = @Content(schema = @Schema(implementation = UserUpdateDto.class))
  )
  @SecuredEndpoint
  @PutMapping("/user/put")
  public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto userDto) {
    long uid = SecurityUtils.getAuthenticatedUserId();
    if (userDto == null) {
      logger.warn("User object is null");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    Optional<User> existingUserOptional = repo.findById((int) uid);
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setProfilePicture(userDto.getProfilePicture());

      // Update roles (fetch existing roles by ID to avoid circular references)
      if (userDto.getRole() != null) {
        Set<Role> roles = userDto.getRole().stream()
            .map(roleInput -> {
              Role role = new Role();
              role.setId(roleInput.getId());
              role.setName(roleInput.getName());
              return role;
            })
            .collect(Collectors.toSet());
        existingUser.setRole(roles);
      }

      existingUser.setActive(userDto.getActive());

      repo.save(existingUser);
      logger.info("User with id {} updated", uid);
      return ResponseEntity.status(HttpStatus.OK).body(existingUser);
    } else {
      logger.warn("User with id {} not found", uid);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  /**
   * Dto for updating limited user information.
   */
  private static class UserUpdateDto {
    private String profilePicture;
    private Set<RoleDto> role;
    private boolean active;

    public Set<RoleDto> getRole() {
      return role;
    }

    public void setRole(Set<RoleDto> roles) {
      this.role = roles;
    }


    public String getProfilePicture() {
      return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
      this.profilePicture = profilePicture;
    }

    public boolean getActive() {
      return active;
    }
    public void setActive(boolean active) {
      this.active = active;
    }
  }

  /**
   * Nested DTO class for automatic Role mapping.
   */
  private static class RoleDto {
    private Long id;
    private String name;

    // Getters and setters
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
