package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a Rest api endpoint for courses.
 */
@RestController
public class CourseController {

  CourseRepo repo;

  @Autowired
  public CourseController(CourseRepo repo) {
    this.repo = repo;
  }

  @GetMapping("/courses")
  public List<Course> getCourses() {
    return repo.findAll();
  }

  @PostMapping("/course")
    public void addCourse(@RequestBody Course course) {
      repo.save(course);
    }
}
