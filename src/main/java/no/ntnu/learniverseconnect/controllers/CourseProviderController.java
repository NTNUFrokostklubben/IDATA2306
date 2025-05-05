package no.ntnu.learniverseconnect.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The CourseProviderController class is a REST controller that handles HTTP requests related to
 * course providers.
 * It provides endpoints for retrieving, adding, and managing course providers in the system.
 *
 * <p>This controller interacts with the CourseProviderRepo to perform CRUD operations on course
 * providers.</p>
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /providers - Retrieves a list of all course providers.</li>
 *   <li>GET /provider/{id} - Retrieves a specific course provider by its ID.</li>
 *   <li>POST /provider - Adds a new course provider to the database.</li>
 * </ul>
 * </p>
 */

@RestController
public class CourseProviderController {

  public static final Logger logger = LoggerFactory.getLogger(CourseProviderController.class);
  CourseProviderRepo repo;

  @Autowired
  public CourseProviderController(CourseProviderRepo repo) {
    this.repo = repo;
  }

  /**
   * Returns a list of all course providers.
   *
   * @return a list of all course providers.
   */
  @GetMapping("/providers")
  public ResponseEntity<List<CourseProvider>> getProviders() {
    logger.info("Fetching all course providers");
    return ResponseEntity.status(200).body(repo.findAll());
  }

  ;

  /**
   * Returns a course provider with the given id.
   *
   * @param id the id of the course provider to return.
   * @return the course provider with the given id.
   */
  @GetMapping("/provider/{id}")
  public ResponseEntity<CourseProvider> getProvider(@PathVariable long id) {
    logger.info("Fetching course provider with id: {}", id);
    int status = 200;
    CourseProvider provider = repo.getCourseProviderById(id);
    if (provider == null) {
      logger.error("Course provider with id {} not found", id);
      status = 404;
    }
    return ResponseEntity.status(status).body(provider);
  }

  ;

  /**
   * Adds a course provider to the database.
   *
   * @param provider the course provider to add.
   * @return a ResponseEntity with the status of the operation.
   */
  @PostMapping("/provider")
  public ResponseEntity<CourseProvider> addProvider(@RequestBody CourseProvider provider) {
    if (provider == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    logger.info("Adding new course provider with id: {}", provider.getId());
    repo.save(provider);
    return ResponseEntity.status(HttpStatus.CREATED).body(provider);
  }

  /**
   * Deletes a course provider with the given id.
   *
   * @param id id of the course provider to delete.
   * @return ResponseEntity with the status of the operation.
   */
  @DeleteMapping("/provider/{id}")
  public ResponseEntity<Void> deleteProvider(@PathVariable long id) {
    logger.info("Deleting course provider with id: {}", id);
    try {
      CourseProvider provider = repo.getCourseProviderById(id);
      if (provider == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      repo.delete(provider);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
