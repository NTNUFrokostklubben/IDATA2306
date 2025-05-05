package no.ntnu.learniverseconnect.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import no.ntnu.learniverseconnect.model.entities.Transaction;
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

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  UserRepo repo;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserRepo repo){
    this.repo = repo;
  }

  /**
   * Get a user by id
   *
   * @param id the id of the user
   * @return the user with the given id
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUserById(@PathVariable long id){
    User user = repo.getUsersById(id);
    if(user == null){
      logger.warn("User with id {} not found", id);
      return ResponseEntity.status(404).body(null);
    }else{
      logger.info("Fetching user with id: {}", id);
      return ResponseEntity.status(200).body(user);
    }
  }

  /**
   * Get the user that matches the email.
   *
   * @param email the email of the user to fetch
   * @return the user with that email
   */
  @GetMapping("/userProfilePicture/{email}")
  public ResponseEntity<String> getProfilePicByEmail(@PathVariable String email){
    Optional<User> userOptional = repo.findUserByEmail(email);
    if (userOptional.isEmpty()){
      logger.warn("User with email {} not found", email);
      return ResponseEntity.status(404).body(null);
    } else{
      User user = userOptional.get();
      String profilePicture = user.getProfilePicture();
      if (profilePicture == null || profilePicture.isEmpty()){
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
   * @param name the email of the user to fetch
   * @return the user with that email
   */
  @GetMapping("/userIdByName/{name}")
  public ResponseEntity<Long> getIdByEmail(@PathVariable String name){
    Optional<User> userOptional = repo.findUserByName(name);
    if (userOptional.isEmpty()){
      logger.warn("ID of user with email {} not found", name);
      return ResponseEntity.status(404).body(null);
    } else{
      User user = userOptional.get();
      Long id = user.getId();
      logger.info("Fetching id for user with name: {}", name);
      return ResponseEntity.status(200).body(id);
    }
  }

  /**
   * Get the total count of users
   *
   * @return the total count of users
   */
  @GetMapping("/users/total")
  public ResponseEntity<Integer> getUserTotal(){
    List<User> totalUsers = repo.findAll();
    return ResponseEntity.status(200).body(totalUsers.size());
  }

  @GetMapping("/users")
  public ResponseEntity<Iterable<User>> getAllUsers(){
    logger.info("Fetching all users");
    return ResponseEntity.status(200).body(repo.findAll());
  }

  /**
   * Get the total users that signed up the last 30 days
   *
   * @return the amount of users for the last 30 days
   */
  @GetMapping("/users/newUsers")
  public ResponseEntity<Float> getNewUsers(){
    List<User> users = repo.findAll();
    float userSum = 0;
    for (User user : users){
      if (user.getUserCreated().after(
          Timestamp.valueOf(LocalDateTime.now().minusDays(30)))){
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
  public ResponseEntity<String> addUser(@RequestBody User user){
    if (user == null) {
      logger.warn("User object is null");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User object is null");
    }

    this.repo.save(user);
    if (repo.existsById(Math.toIntExact(user.getId()))){
      return ResponseEntity.status(HttpStatus.CREATED).body("User with id " + user.getId() + " saved");
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
  public ResponseEntity<String> deleteUser(@PathVariable int id){
    if(repo.existsById(id)){
      logger.info("Deleting user with id: {}", id);
      repo.deleteById(id);
      return ResponseEntity.status(200).body("User with id " + id + " deleted");
    }else{
      logger.warn("User with id {} not found", id);
      return ResponseEntity.status(404).body("User with id " + id + " not found");
    }
  }
}
