package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Course Providers", description = "APIs for managing course providers")
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
  @Operation(summary = "Get all providers",
      description = "Retrieves list of all course providers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = CourseProvider.class,type = "array")))
  })
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
  @Operation(summary = "Get provider by ID",
      description = "Retrieves specific course provider details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = CourseProvider.class))),
      @ApiResponse(responseCode = "404", description = "Provider not found")
  })
  @GetMapping("/provider/{id}")
  public ResponseEntity<CourseProvider> getProvider(@PathVariable long id) {
    logger.info("Fetching course provider with id: {}", id);
    int status = 200;
    CourseProvider provider = repo.getCourseProviderById(id);
    if (provider == null) {
      logger.error("Course provider with id {} not found", id);
      status = 404;
    }
    logger.info("Found course provider with id: {}", id);
    return ResponseEntity.status(status).body(provider);
  }

  ;

  /**
   * Adds a course provider to the database.
   *
   * @param provider the course provider to add.
   * @return a ResponseEntity with the status of the operation.
   */

  @Operation(summary = "Add new provider",
      description = "Creates a new course provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          content = @Content(schema = @Schema(implementation = CourseProvider.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Course provider to be added",
      content = @Content(schema = @Schema(implementation = CourseProvider.class))
  )
  @PostMapping("/provider")
  public ResponseEntity<CourseProvider> addProvider(@RequestBody CourseProvider provider) {
    if (provider == null) {
      logger.error("Course provider is null");
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
  @Operation(summary = "Delete provider",
      description = "Removes a course provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Provider not found")
  })
  @DeleteMapping("/provider/{id}")
  public ResponseEntity<Void> deleteProvider(@PathVariable long id) {
    logger.info("Deleting course provider with id: {}", id);
    try {
      CourseProvider provider = repo.getCourseProviderById(id);
      if (provider == null) {
        logger.error("Course provider with id {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      repo.delete(provider);
      logger.info("Deleted course provider with id: {}", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      logger.error("Course provider with id {} not found", id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
