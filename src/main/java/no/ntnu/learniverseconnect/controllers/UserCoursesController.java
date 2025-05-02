package no.ntnu.learniverseconnect.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Review;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.ReviewRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the rest controller for the User_course table in the database.
 */

@RestController
public class UserCoursesController {

  UserCoursesRepo userCoursesRepo;
  UserRepo userRepo;
  CourseRepo courseRepo;
    ReviewRepo reviewRepo;


  @Autowired
  public UserCoursesController(UserCoursesRepo userCoursesRepo1, UserRepo userRepo1,
                               CourseRepo courseRepo1, ReviewRepo reviewRepo1) {
    this.userCoursesRepo = userCoursesRepo1;
    this.courseRepo = courseRepo1;
    this.userRepo = userRepo1;
    this.reviewRepo = reviewRepo1;
  }

  /**
   * Get all courses associated with a user.
   *
   * @param id the user to get by
   * @return the list of courses a user is associated with.
   */
  @GetMapping("/userCourses/{id}")
  public ResponseEntity<List<UserCourse>> getAll(@PathVariable long id) {
    List<UserCourse> userCourseList = userCoursesRepo.getAllByUser_Id(id);
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
    List<UserCourse> courses = userCoursesRepo.getAllByCourse_Id(cid);
    if (courses.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    float average = 0;
    int count = 0;
    int status = 0;
    for (UserCourse course : courses) {
      if (course.getReview().getRating() > 0 && course.getReview().getRating() < 6) {
        average += course.getReview().getRating();
        count++;
      }
    }
    if (count <= 0) {
      status = 404;
      average = 0;
    } else {
      average = average / count;
      if (average > 0 && average < 6) {
        status = 200;
      } else {
        status = 204;
      }
    }
    return ResponseEntity.status(status).body(average);
  }

  /**
   * Get all user courses associated with a course.
   *
   * @param cid the course to get by
   * @return the list of courses a course is associated with.
   */
  @GetMapping("/userCourses/course/{cid}")
  public ResponseEntity<List<UserCourse>> getAllByCourse(@PathVariable long cid) {
    List<UserCourse> userCourseList = userCoursesRepo.getAllByCourse_Id(cid);
    int status = 404;
    if (!userCourseList.isEmpty()) {
      status = 200;
    }
    return ResponseEntity.status(status).body(userCourseList);
  }

  @Transactional
  @PutMapping("/userCourses/addRating/{uid}/{cid}")
  public ResponseEntity<Void> addRating(@RequestBody Review review, @PathVariable long uid,
                                        @PathVariable long cid) {
    if(review == null){
      return ResponseEntity.status(400).build();
    }
    if (review.getRating() < 1) {
      review.setRating(1);
    }
    review.setDate();
    UserCourse userCourse = userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(cid, uid);
    if(userCourse.getReview() != null){
      reviewRepo.delete(userCourse.getReview());
    }
    userCourse.setReview(review);
    userCoursesRepo.save(userCourse);
    reviewRepo.save(review);
    return ResponseEntity.status(200).build();

  }
}

