package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceAndRatingDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
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
@Tag(name = "Courses", description = "APIs for managing courses")
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
  @Operation(summary = "Get all courses",
      description = "Retrieves list of all courses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = Course.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No courses found")
  })
  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getCourses() {
    logger.info("Fetching all courses");
    List<Course> courses = courseRepo.findAll();
    if (courses.isEmpty()) {
      logger.warn("No courses found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Found {} courses", courses.size());
      return ResponseEntity.status(200).body(courses);
    }
  }

  /**
   * Returns the amount of courses in the database.
   *
   * @return the amount of courses in the database.
   */
  @Operation(summary = "Get course count",
      description = "Returns total number of courses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = Integer.class))),
      @ApiResponse(responseCode = "404", description = "No courses found")
  })
  @GetMapping("/courses/total")
  public ResponseEntity<Integer> getCourseTotal() {
    logger.info("Fetching total number of courses");
    List<Course> totalCourses = courseRepo.findAll();
    if (totalCourses.isEmpty()) {
      logger.warn("No courses found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Found {} courses", totalCourses.size());
      return ResponseEntity.status(200).body(totalCourses.size());
    }
  }

  /**
   * Adds a course to the database.
   *
   * @param course the course to add.
   */
  @Operation(summary = "Create course",
      description = "Adds a new course to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          content = @Content(schema = @Schema(implementation = Course.class)))
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Course object to be added",
      content = @Content(schema = @Schema(implementation = Course.class))
  )
  @PostMapping("/course")
  public ResponseEntity<Course> addCourse(@RequestBody Course course) {
    if(course == null){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
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
  @Operation(summary = "Get course by ID",
      description = "Retrieves specific course details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = Course.class))),
      @ApiResponse(responseCode = "404", description = "Course not found")
  })

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
   * Returns the dto's needed to show the course card.
   *
   * @return the dto's needed to show the course card.
   */
  @Operation(summary = "Get course cards",
      description = "Retrieves course cards with min price/rating")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = CourseWithMinPriceAndRatingDto.class,
              type = "array"))),
      @ApiResponse(responseCode = "404", description = "No courses found")
  })
  @GetMapping("/courses/courseCard")
  public ResponseEntity<List<CourseWithMinPriceAndRatingDto>> getOfferableCoursesByCourseCard() {
    List<OfferableCourses> courses = offerableCoursesRepo.findAll();
    List<CourseWithMinPriceAndRatingDto> filteredResult = new ArrayList<>(courses.stream()
     .collect(Collectors.groupingBy(
         OfferableCourses::getCourse,
         Collectors.collectingAndThen(
             Collectors.toList(),
             list -> {
               // Find offer with min discounted price and closest date
               OfferableCourses bestOffer = list.stream()
                  .min(Comparator.comparingDouble(
                                     (OfferableCourses o) -> o.getPrice() * (1 - o.getDiscount()))
                                 .thenComparing(OfferableCourses::getDate))
                  .orElseThrow();

               // Calculate average rating (assuming UserCourseRepo is available)
               Float avgRating =
                       userCoursesRepo.getAverageRatingByCourseId(bestOffer.getCourse().getId());
               int numberOfRatings =
                   userCoursesRepo.countByCourseId(bestOffer.getCourse().getId());
               return new CourseWithMinPriceAndRatingDto(
                   bestOffer.getCourse(),
                   bestOffer.getPrice() * (1 - bestOffer.getDiscount()),
                   bestOffer.getDate(),
                   avgRating != null ? avgRating.floatValue() : 0f,
                   numberOfRatings
               );
             }
         )
     )).values());
    if (filteredResult.isEmpty()) {
      logger.warn("No courses found");
      return ResponseEntity.status(404).body(null);
    } else if (filteredResult.size()>10) {
      filteredResult.subList(0,10);
    }
    logger.info("Found {} courses", filteredResult.size());
    return ResponseEntity.status(200).body(filteredResult);
  }


  /**
   * Updates a course in the database.
   *
   * @param course the course to update.
   */
  @Operation(summary = "Update course",
      description = "Modifies existing course details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = @Content(schema = @Schema(implementation = Course.class)))
  })
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
  @Operation(summary = "Delete course",
      description = "Removes a course and all related data")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Course not found")
  })
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
