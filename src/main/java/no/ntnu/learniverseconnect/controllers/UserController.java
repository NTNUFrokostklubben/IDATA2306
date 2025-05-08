package no.ntnu.learniverseconnect.controllers;

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
   * @param id the id of the user
   * @return the user with the given id
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUserById(@PathVariable long id) {
    User user = repo.getUsersById(id);
    if (user == null) {
      logger.warn("User with id {} not found", id);
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching user with id: {}", id);
      return ResponseEntity.status(200).body(user);
    }
  }

  /**
   * Get safe user dto by email.
   *
   * @param email the id of the user
   * @return the user with the given id
   */
  @GetMapping("/userDto/{email}")
  public ResponseEntity<ReduxUserDto> getReduxUserDtoById(@PathVariable String email) {
    User user = repo.getUsersByEmail(email);
    if (user == null) {
      logger.warn("User with email {} not found", email);
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching user with email: {}", email);
      return ResponseEntity.status(200).body( new ReduxUserDto(user.getEmail(), user.getId()));
    }
  }

  /**
   * Get the user that matches the email.
   *
   * @param email the email of the user to fetch
   * @return the user with that email
   */
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
   * Get the user id that matches the email.
   *
   * @param email the email of the user to fetch
   * @return the user with that email
   */
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
  @GetMapping("/users/total")
  public ResponseEntity<Integer> getUserTotal() {
    List<User> totalUsers = repo.findAll();
    return ResponseEntity.status(200).body(totalUsers.size());
  }

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
  @GetMapping("/users/newUsers")
  public ResponseEntity<Float> getNewUsers() {
    List<User> users = repo.findAll();
    float userSum = 0;
    for (User user : users) {
      if (user.getUserCreated().after(
          Timestamp.valueOf(LocalDateTime.now().minusDays(30)))) {
        userSum++;
      }
    }
    return ResponseEntity.status(200).body(userSum);
  }

  /**
   * Adds a new user to the database.
   *
   * @param user the user to add
   * @return a response entity with the status of the operation
   */
  @PostMapping("/user")
  public ResponseEntity<String> addUser(@RequestBody User user) {
    if (user == null) {
      logger.warn("User object is null");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User object is null");
    }

    this.repo.save(user);
    if (repo.existsById(Math.toIntExact(user.getId()))) {
      return ResponseEntity.status(HttpStatus.CREATED).body(
          "User with id " + user.getId() + " saved");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Deletes a user from the database.
   *
   * @param id the id of the user to delete
   * @return a response entity with the status of the operation
   */
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
   * Updates a user in the database.
   *
   * @param id the id of the user to update
   * @param userDto the user object with updated information (profile picture, roles, active)
   * @return a response entity with the status of the operation
   */
  @PutMapping("/user/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable long id, @RequestBody UserUpdateDto userDto) {
    if (userDto == null) {
      logger.warn("User object is null");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    Optional<User> existingUserOptional = repo.findById((int) id);
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
      return ResponseEntity.status(HttpStatus.OK).body(existingUser);
    } else {
      logger.warn("User with id {} not found", id);
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
