package no.ntnu.learniverseconnect.controllers;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
  CourseRepo repo;


  /**
   * Creates a new instance of the CourseController using JPA black magic.
   *
   * @param repo the course repo interface.
   */
  @Autowired
  public CourseController(CourseRepo repo) {
    this.repo = repo;
  }

  /**
   * Returns a list of all courses.
   *
   * @return a list of all courses.
   */
  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getCourses() {
    logger.info("Fetching all courses");
    return ResponseEntity.status(200).body(repo.findAll());
  }

  /**
   * Adds a course to the database.
   *
   * @param course the course to add.
   */
  @PostMapping("/course")
  public ResponseEntity<Course> addCourse(@RequestBody Course course) {
    logger.info("Adding course: {}", course.getId());
    repo.save(course);
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
    return ResponseEntity.status(200).body(repo.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Course not found with id: " + id)));
  }
}
