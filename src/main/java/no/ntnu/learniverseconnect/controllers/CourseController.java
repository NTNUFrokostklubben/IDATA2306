package no.ntnu.learniverseconnect.controllers;

import jakarta.transaction.Transactional;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.KeywordsRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
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
 * The CourseController class is a REST controller that handles HTTP requests related to courses.
 * It provides endpoints for retrieving, adding, and managing courses in the system.
 *
 * <p>This controller interacts with the CourseRepo to perform CRUD operations on courses.</p>
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /courses - Retrieves a list of all courses.</li>
 *   <li>POST /course - Adds a new course to the database.</li>
 *   <li>GET /course/{id} - Retrieves a specific course by its ID.</li>
 * </ul>
 * </p>
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
  CourseRepo courseRepo;
  OfferableCoursesRepo offerableCoursesRepo;
  KeywordsRepo keywordsRepo;
  UserCoursesRepo userCoursesRepo;


  /**
   * Creates a new instance of the CourseController using JPA black magic.
   *
   * @param repo the course repo interface.
   */
  @Autowired
  public CourseController(CourseRepo repo, OfferableCoursesRepo offerableCoursesRepo,
                          KeywordsRepo keywordsRepo, UserCoursesRepo userCoursesRepo) {
    this.courseRepo = repo;
    this.offerableCoursesRepo = offerableCoursesRepo;
    this.keywordsRepo = keywordsRepo;
    this.userCoursesRepo = userCoursesRepo;
  }

  /**
   * Returns a list of all courses.
   *
   * @return a list of all courses.
   */
  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getCourses() {
    logger.info("Fetching all courses");
    return ResponseEntity.status(200).body(courseRepo.findAll());
  }

  /**
   * Returns the amount of courses in the database.
   *
   * @return the amount of courses in the database.
   */
  @GetMapping("/courses/total")
  public ResponseEntity<Integer> getCourseTotal() {
    logger.info("Fetching total number of courses");
    List<Course> totalCourses = courseRepo.findAll();
    return ResponseEntity.status(200).body(totalCourses.size());
  }

  /**
   * Adds a course to the database.
   *
   * @param course the course to add.
   */
  @PostMapping("/course")
  public ResponseEntity<Course> addCourse(@RequestBody Course course) {
    logger.info("Adding course: {}", course.getId());
    courseRepo.save(course);
    return ResponseEntity.status(HttpStatus.CREATED).body(course);
  }

  /**
   * Returns a course with the given id.
   *
   * @param id the id of the course to return.
   * @return the course with the given id.
   */
  @GetMapping("/course/{id}")
  public ResponseEntity<Course> getCourse(@PathVariable int id) {
    logger.info("Fetching course with id: {}", id);
    Course course = courseRepo.getCoursesById(id);
    if (course == null) {
      logger.warn("Course with id {} not found", id);
      return ResponseEntity.status(404).body(null);
    } else {
      return ResponseEntity.status(200).body(course);
    }
  }

  /**
   * Updates a course in the database.
   *
   * @param course the course to update.
   */
  @PutMapping("/course")
  public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
    logger.info("Updating course with id: {}", course.getId());

    return ResponseEntity.status(200).body(courseRepo.save(course));
  }

  /**
   * Deletes a course from the database.
   *
   * @param id the id of the course to delete.
   */
  @Transactional
  @DeleteMapping("/course/{id}")
  public ResponseEntity<String> deleteCourse(@PathVariable int id) {
    logger.info("Deleting course with id: {}", id);
    Course course = courseRepo.getCoursesById(id);
    if (course == null) {
      logger.warn("Course with id {} not found", id);
      return ResponseEntity.status(404).body("Course with id " + id + " not found");
    }
    offerableCoursesRepo.deleteAllByCourse_Id(course.getId());
    keywordsRepo.deleteAllByCourse_Id(id);
    userCoursesRepo.deleteAllByCourse_Id(id);
    courseRepo.delete(course);
    return ResponseEntity.status(204).body("Course deleted successfully");
  }
}
