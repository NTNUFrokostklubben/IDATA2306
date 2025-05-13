package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for offerable courses.
 */
@RestController
@Tag(name = "Offerable Courses",
    description = "APIs for managing course offerings by providers")
public class OfferableCoursesController {

  private final CourseRepo courseRepo;
  private final OfferableCoursesRepo repo;
  private final CourseProviderRepo courseProviderRepo;
  private final Logger logger = Logger.getLogger(OfferableCoursesController.class.getName());


  @Autowired
  public OfferableCoursesController(OfferableCoursesRepo repo, CourseRepo courseRepo,
                                    CourseProviderRepo courseProviderRepo) {
    this.repo = repo;
    this.courseRepo = courseRepo;
    this.courseProviderRepo = courseProviderRepo;
  }

  /**
   * Returns a list of all offerable courses with provider information.
   *
   * @return a list of all offerable courses.
   */
  @Operation(summary = "Get all offerable courses",
      description = "Retrieves all available course offerings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(
              implementation = OfferableCourses.class,
              type = "array")))
  })
  @GetMapping("/offerableCourses")
  public ResponseEntity<List<OfferableCourses>> getOfferableCourses() {
    logger.info("Fetching all offerable courses");
    return ResponseEntity.status(200).body(repo.findAll());
  }

  /**
   * Returns an offerable course with the given id.
   *
   * @param id id of the offerable course to return.
   * @return offerable course with the given id.
   */
  @Operation(summary = "Get offerable course by ID",
      description = "Retrieves a specific course offering")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = OfferableCourses.class))),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  @GetMapping("/offerableCourses/{id}")
  public ResponseEntity<OfferableCourses> getOfferableCourseById(@PathVariable int id) {
    OfferableCourses offerableCourse = repo.findById(id).orElse(null);
    if (offerableCourse != null) {
      logger.info("Offerable course found with id: " + id);
      return ResponseEntity.status(200).body(offerableCourse);
    } else {
      logger.warning("Offerable course not found with id: " + id);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Deletes an offerable course with the given id.
   *
   * @param id id of the offerable course to delete.
   * @return response entity with status code 200 if the course was deleted, 404 if not found.
   */
  @Operation(summary = "Delete offerable course",
      description = "Removes a course offering")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  @DeleteMapping("/offerableCourses/{id}")
  public ResponseEntity<Void> deleteOfferableCourse(@PathVariable long id) {
    OfferableCourses offerableCourse = repo.getOfferableCoursesById(id);
    if (offerableCourse != null) {
      logger.info("Deleting offerable course with id: " + id);
      repo.delete(offerableCourse);
      return ResponseEntity.status(200).build();
    } else {
      logger.warning("Offerable course not found with id: " + id);
      return ResponseEntity.status(404).build();
    }
  }


  /**
   * Returns a list of offerable courses for a given course id.
   *
   * @param cid Course id.
   * @return List of offerable courses for the given course id.
   */
  @Operation(summary = "Get courses by parent course ID",
      description = "Finds all offerings for a base course")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = OfferableCourses.class,
              type = "array"))),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  @GetMapping("/offerableCourses/course/{cid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByCourseId(
      @PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);
    if (!list.isEmpty()) {
      logger.info("Offerable courses found for course with id: " + cid);
      return ResponseEntity.status(200).body(list);
    } else {
      logger.warning("No offerable courses found for course with id: " + cid);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Returns the lowest price of offerable courses for a given course id.
   */
  @Operation(summary = "Get lowest price by course",
      description = "Finds minimum price across all offerings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(
              implementation = Float.class))),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  //TODO delete?
  @GetMapping("/offerableCourses/lowestPrice/course/{cid}")
  public ResponseEntity<Float> getOfferablePriceByCourseId(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);

    float lowestPrice;
    if (list.isEmpty()) {
      logger.warning("No offerable courses found for course with id: " + cid);
      return ResponseEntity.status(404).body(null);
    } else {
      lowestPrice = list.get(0).getPrice() * (1 - list.get(0).getDiscount());
      for (OfferableCourses oc : list) {
        float discountedPrice = oc.getPrice() * (1 - oc.getDiscount());
        if (discountedPrice < lowestPrice) {
          lowestPrice = discountedPrice;
        }
      }
    }
    logger.info("Lowest price found for course with id: " + cid + " is: " + lowestPrice);
    return ResponseEntity.status(200).body(lowestPrice);
  }

  /**
   * Returns the soonest date of offerable courses for a given course id.
   */
  @Operation(summary = "Get soonest date by course",
      description = "Finds earliest available course date")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(
              implementation = Date.class))),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  //TODO delete?
  @GetMapping("/offerableCourses/closestDate/course/{cid}")
  public ResponseEntity<Date> getOfferableClosestDateByCourseID(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);

    Date closestDate;
    if (list.isEmpty()) {
      logger.warning("No offerable courses found for course with id: " + cid);
      return ResponseEntity.status(404).body(null);
    } else {
      closestDate = list.get(0).getDate();
      for (OfferableCourses oc : list) {
        if (oc.getDate().before(closestDate)) {
          closestDate = oc.getDate();
        }
      }
    }
    logger.info("Closest date found for course with id: " + cid + " is: " + closestDate);
    return ResponseEntity.status(200).body(closestDate);
  }

  /**
   * Returns a list of offerable courses for a given provider id.
   *
   * @param pid Provider id.
   * @return List of offerable courses for the given provider id.
   */
  @Operation(summary = "Get offerings by provider",
      description = "Lists all courses offered by a provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(
              implementation = OfferableCourses.class,
              type = "array"))),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  @GetMapping("/offerableCourses/provider/{pid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByProviderId(
      @PathVariable long pid) {
    List<OfferableCourses> list = repo.getAllByProvider_Id(pid);
    if (!list.isEmpty()) {
      logger.info("Offerable courses found for provider with id: " + pid);
      return ResponseEntity.status(200).body(list);
    } else {
      logger.warning("No offerable courses found for provider with id: " + pid);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Adds an offerable course to the database.
   * Updates "Closest Course" if the new date is sooner than the current closest date.
   *
   * @param offerableCourse the offerable course to add.
   */
  @Operation(summary = "Create new course offering",
      description = "Adds a new provider course offering")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          content = @Content(schema = @Schema(implementation = OfferableCourses.class)))
  })
  @PostMapping("/offerableCourse")
  public ResponseEntity<OfferableCourses> addOfferableCourse(
      @RequestBody OfferableCourses offerableCourse) {
    repo.save(offerableCourse);

    // Get the associated course
    Course course = courseRepo.getById(offerableCourse.getCourse().getId());
    Date newCourseDate = offerableCourse.getDate();
    offerableCourse.setCourse(course);

    // Get the associated course provider
    CourseProvider provider = courseProviderRepo.getCourseProviderById(offerableCourse.getProvider().getId());
    offerableCourse.setProvider(provider);

    if (course.getClosestCourse() == null || newCourseDate.before(course.getClosestCourse())) {
      course.setClosestCourse(newCourseDate);
      courseRepo.save(course);
    }

    logger.info("Offerable course added with id: " + offerableCourse.getId());
    return ResponseEntity.status(201).body(offerableCourse);
  }
}
