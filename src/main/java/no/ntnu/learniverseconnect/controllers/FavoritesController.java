package no.ntnu.learniverseconnect.controllers;

import jakarta.transaction.Transactional;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The FavoritesController class is responsible for managing user favorites.
 * It provides methods to add, remove, and retrieve favorite items for a specific user.
 * This controller interacts with the service layer to perform business logic operations
 * and returns appropriate responses to the client.
 */

@RestController
public class FavoritesController {

  private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);
  private final UserRepo userRepo;
  private final CourseRepo courseRepo;
  private FavoritesRepo repo;

  /**
   * Constructor for FavoritesController.
   *
   * @param repo The repository for managing favorite items.
   * @param userRepo The repository for managing user data.
   * @param courseRepo The repository for managing course data.
   */
  @Autowired
  public FavoritesController(FavoritesRepo repo, UserRepo userRepo, CourseRepo courseRepo) {
    this.repo = repo;
    this.userRepo = userRepo;
    this.courseRepo = courseRepo;
  }

  /**
   * Retrieves all favorite items for a specific user by their user ID.
   *
   * @param id The unique identifier of the user.
   * @return A ResponseEntity containing a list of Favorite objects if found, or a 404 status
   * if no favorites are found.
   */
  @GetMapping("/userFavorites/{id}")
  public ResponseEntity<List<Favorite>> getFavoritesForUser(@PathVariable long id) {
    logger.info("Fetching favorites for user with id: {}", id);
    int status = 0;
    List<Favorite> list = repo.getAllByUser_Id(id);
    if (!list.isEmpty()) {
      status = 200;
    } else {
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
  public ResponseEntity<Integer> getCountOfFavorites(@PathVariable long id) {
    logger.info("Fetching favorite count for course with id: {}", id);
    return ResponseEntity.status(200).body(repo.getByCourse_Id(id).size());
  }

  /**
   * Adds a course to a user's list of favorites.
   *
   * @param uid The unique identifier of the user.
   * @param cid The unique identifier of the course.
   * @return A ResponseEntity with a 200 status if the favorite is successfully added.
   */
  @PostMapping("/addFavorite/{uid}/{cid}")
  public ResponseEntity<Void> addFavorite(@PathVariable long uid, @PathVariable long cid) {
    logger.info("Adding favorite for user with id: {} and course with id: {}", uid, cid);
    int status = 200;
    User user = userRepo.getUsersById(uid);
    Course course = courseRepo.getCoursesById(cid);
    Favorite favorite = new Favorite(user, course);
    if (repo.existsByCourse_IdAndUser_Id(cid, uid)) {
      status = 400;
    } else if (user == null || course == null) {
      status = 404;
    } else {
      repo.save(favorite);
    }
    return ResponseEntity.status(status).body(null);
  }


    /**
     * Removes a course from a user's list of favorites.
     *
     * @param uid The unique identifier of the user.
     * @param cid The unique identifier of the course.
     * @return A ResponseEntity with a 200 status if the favorite is successfully removed.
     */
    @Transactional
    @DeleteMapping("/removeFavorite/{uid}/{cid}")
    public ResponseEntity<Void> removeFavorite(@PathVariable long uid, @PathVariable long cid){
      logger.info("Removing favorite for user with id: {} and course with id: {}", uid, cid);
      int status = 200;
      if (repo.existsByCourse_IdAndUser_Id(cid, uid)) {
        repo.deleteFavoriteByCourse_IdAndUser_Id(cid, uid);
      } else {
        status = 400;
      }
      return ResponseEntity.status(status).body(null);
    }

  /**
   * Check if a course is already favorited by a user.
   *
   * @param uid The unique identifier of the user.
    * @param cid The unique identifier of the course.
   */
    @GetMapping("/isFavorited/{uid}/{cid}")
    public ResponseEntity<Boolean> isFavorited(@PathVariable long uid, @PathVariable long cid){
      logger.info("Checking if course with id: {} is favorited by user with id: {}", cid, uid);
      int status = 200;
      boolean isFavorited = repo.existsByCourse_IdAndUser_Id(cid, uid);
      return ResponseEntity.status(status).body(isFavorited);
    }
}
