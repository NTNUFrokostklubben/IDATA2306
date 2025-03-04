package no.ntnu.learniverseconnect.controllers;

import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  UserRepo repo;

  @Autowired
  public UserController(UserRepo repo){
    this.repo = repo;
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUser(@PathVariable long id){
    User user = repo.getUsersById(id);
    if(user == null){
      return ResponseEntity.status(404).body(null);
    }else{
      return ResponseEntity.status(200).body(user);
    }
  }
}
