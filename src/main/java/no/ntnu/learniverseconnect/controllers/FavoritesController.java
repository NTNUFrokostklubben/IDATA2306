package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.Favorite;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.FavoritesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The FavoritesController class is responsible for managing user favorites.
 * It provides methods to add, remove, and retrieve favorite items for a specific user.
 * This controller interacts with the service layer to perform business logic operations
 * and returns appropriate responses to the client.
 *
 *
 */

@RestController
public class FavoritesController {

  private final UserRepo userRepo;
  private final CourseRepo courseRepo;
  private FavoritesRepo repo;
  private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);

  @Autowired
  public FavoritesController(FavoritesRepo repo, UserRepo userRepo, CourseRepo courseRepo){
    this.repo = repo;
    this.userRepo = userRepo;
    this.courseRepo = courseRepo;
  }

  /**
   * Retrieves all favorite items for a specific user by their user ID.
   *
   * @param id The unique identifier of the user.
   * @return A ResponseEntity containing a list of Favorite objects if found, or a 404 status if no favorites are found.
   */
  @GetMapping("/userFavorites/{id}")
  public ResponseEntity<List<Favorite>> getFavoritesForUser(@PathVariable long id) {
    logger.info("Fetching favorites for user with id: {}", id);
    int status = 0;
    List<Favorite> list = repo.getAllByUser_Id(id);
    if(!list.isEmpty()){
      status = 200;
    }else{
      status = 404;
      list = null;
    }

    return ResponseEntity.status(status).body(list);
  }
  /**
   * Retrieves the count of favorites for a specific course by its course ID.
   *
   * @param id The unique identifier of the course.
   * @return A ResponseEntity containing the count of favorites for the specified course.
   */
  @GetMapping("/favoriteCount/{id}")
  public ResponseEntity<Integer> getCountOfFavorites(@PathVariable long id){
    logger.info("Fetching favorite count for course with id: {}", id);
    return  ResponseEntity.status(200).body(repo.getByCourse_Id(id).size());
  }

  /**
   * Adds a course to a user's list of favorites.
   *
   * @param uid The unique identifier of the user.
   * @param cid The unique identifier of the course.
   * @return A ResponseEntity with a 200 status if the favorite is successfully added.
   */
  @PostMapping("/addFavorite/{uid}/{cid}")
  public ResponseEntity<Void> addFavorite(@PathVariable long uid, @PathVariable long cid){
    logger.info("Adding favorite for user with id: {} and course with id: {}", uid, cid);
    User user = userRepo.getUsersById(uid);
    Course course = courseRepo.getCoursesById(cid);
    Favorite favorite = new Favorite(user, course);
    repo.save(favorite);
    return ResponseEntity.status(200).body(null);
  }
}
