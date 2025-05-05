package no.ntnu.learniverseconnect.controllers;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the rest controller for the User_course table in the database.
 */

@RestController
public class UserCoursesController {

  private static final Logger logger = LoggerFactory.getLogger(UserCoursesController.class);
  UserCoursesRepo repo;
  UserRepo userRepo;
  CourseRepo courseRepo;

  /**
   * Constructor for UserCoursesController.
   *
   * @param userCoursesRepo1 the repository for user courses
   * @param userRepo1 the repository for users
   * @param courseRepo1 the repository for courses
   */
  @Autowired
  public UserCoursesController(UserCoursesRepo userCoursesRepo1, UserRepo userRepo1,
                               CourseRepo courseRepo1) {
    this.repo = userCoursesRepo1;
    this.courseRepo = courseRepo1;
    this.userRepo = userRepo1;
  }

  /**
   * Get all courses associated with a user.
   *
   * @param id the user to get by
   * @return the list of courses a user is associated with.
   */
  @GetMapping("/userCourses/{id}")
  public ResponseEntity<List<UserCourse>> getAll(@PathVariable long id) {
    List<UserCourse> userCourseList = repo.getAllByUser_Id(id);
    int status = 404;
    if (!userCourseList.isEmpty()) {
      status = 200;
    }
    return ResponseEntity.status(status).body(userCourseList);
  }

  /**
   * Get the average rating of a given course based on the course id.
   *
   * @param cid the course id for the course to find the average on.
   * @return the average.
   */
  @GetMapping("/userCourses/averageRating/{cid}")
  public ResponseEntity<Float> getAverageByCourse(@PathVariable long cid) {
    List<UserCourse> courses = repo.getAllByCourse_Id(cid);
    if (courses.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    float average = 0;
    int status = 0;
    for (UserCourse course : courses) {
      average += course.getRating();
    }
    if (average > -1 && average < 6) {
      status = 200;
    } else {
      status = 204;
    }
    return ResponseEntity.status(status).body(average / courses.size());
  }

  /**
   * Get all reviews from the database.
   *
   * @return all reviews
   */
  @GetMapping("/userCourses")
  public ResponseEntity<Iterable<UserCourse>> getAllReviews() {
    logger.info("Fetching all reviews");
    return ResponseEntity.status(200).body(repo.findAll());
  }

  /**
   * Get the last ten reviews from the database.
   *
   * @return the last ten reviews
   */
  @GetMapping("/userCourses/lastReviews")
  public ResponseEntity<Iterable<UserCourse>> getLastReviews() {
    logger.info("Fetching the ten last reviews");
    List<UserCourse> userCourseList = repo.findAll();
    List<UserCourse> lastReviews = new ArrayList<>();
    userCourseList.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
    lastReviews.addAll(userCourseList.subList(0, Math.min(10, userCourseList.size())));
    return ResponseEntity.status(200).body(lastReviews);
  }


  /**
   * Adds a new review to the database.
   *
   * @param userCourse the review to add
   * @return a response entity with the status of the operation
   */
  @PostMapping("/userCourses/review")
  public ResponseEntity<String> addNewReview(@RequestBody UserCourse userCourse) {
    this.repo.save(userCourse);
    if (repo.existsById(Math.toIntExact(userCourse.getId()))) {
      return ResponseEntity.status(HttpStatus.CREATED).body(
          "Review with id " + userCourse.getId() + " saved");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
