package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a Rest api endpoint for courses.
 */
@RestController
public class CourseController {

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
    return ResponseEntity.status(200).body(repo.findAll());
  }

  /**
   * Adds a course to the database.
   *
   * @param course the course to add.
   */
  @PostMapping("/course")
  public void addCourse(@RequestBody Course course) {
    repo.save(course);
  }

  /**
   * Returns a course with the given id.
   *
   * @param id the id of the course to return.
   * @return the course with the given id.
   */

  @GetMapping("/course/{id}")
  public ResponseEntity<Course> getCourse(@PathVariable int id) {
    return ResponseEntity.status(200).body(repo.findById(id).orElse(null));
  }
}
