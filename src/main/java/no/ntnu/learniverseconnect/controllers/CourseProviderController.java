package no.ntnu.learniverseconnect.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
 *  providers.</p>
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

  CourseProviderRepo repo;
  public static final Logger logger = LoggerFactory.getLogger(CourseProviderController.class);

  @Autowired
  public CourseProviderController(CourseProviderRepo repo) {
    this.repo = repo;
  }

  /**
   * Returns a list of all course providers.
   * @return a list of all course providers.
   */
  @GetMapping("/providers")
    public ResponseEntity<List<CourseProvider>> getProviders() {
        logger.info("Fetching all course providers");
        return ResponseEntity.status(200).body(repo.findAll());
    };
  /**
   * Returns a course provider with the given id.
   * @param id the id of the course provider to return.
   * @return the course provider with the given id.
   */
  @GetMapping("/provider/{id}")
    public ResponseEntity<CourseProvider> getProvider(@PathVariable int id) {
        logger.info("Fetching course provider with id: {}", id);
        return ResponseEntity.status(200).body(repo.findById(id).orElseThrow(() ->
    new EntityNotFoundException("Course not found with id: " + id)));
    };

    /**
     * Adds a course provider to the database.
     * @param provider the course provider to add.
     */
  @PostMapping("/provider")
    public void addProvider(@RequestBody CourseProvider provider) {
        logger.info("Adding new course provider");
        repo.save(provider);
    }
}
