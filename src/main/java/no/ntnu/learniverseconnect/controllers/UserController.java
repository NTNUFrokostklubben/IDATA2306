package no.ntnu.learniverseconnect.controllers;

import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  UserRepo repo;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserRepo repo){
    this.repo = repo;
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUser(@PathVariable long id){
    logger.info("Fetching user with id: {}", id);
    User user = repo.getUsersById(id);
    if(user == null){
      return ResponseEntity.status(404).body(null);
    }else{
      return ResponseEntity.status(200).body(user);
    }
  }

  @GetMapping("/users")
  public ResponseEntity<Iterable<User>> getAllUsers(){
    logger.info("Fetching all users");
    return ResponseEntity.status(200).body(repo.findAll());
  }
}
