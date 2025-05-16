package no.ntnu.learniverseconnect.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.Review;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.ReviewRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import no.ntnu.learniverseconnect.security.SecuredEndpoint;
import no.ntnu.learniverseconnect.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the rest controller for the User_course table in the database.
 */

@RestController
@Tag(name = "User Courses", description = "APIs for managing user-course relationships and reviews")
public class UserCoursesController {

  UserCoursesRepo userCoursesRepo;
  UserRepo userRepo;
  CourseRepo courseRepo;
  ReviewRepo reviewRepo;
  private static final Logger logger = LoggerFactory.getLogger(UserCoursesController.class);

  /**
   * Constructor for UserCoursesController.
   *
   * @param userCoursesRepo1 the repository for user courses
   * @param userRepo1        the repository for users
   * @param courseRepo1      the repository for courses
   */
  @Autowired
  public UserCoursesController(UserCoursesRepo userCoursesRepo1, UserRepo userRepo1,
                               CourseRepo courseRepo1, ReviewRepo reviewRepo1) {
    this.userCoursesRepo = userCoursesRepo1;
    this.courseRepo = courseRepo1;
    this.userRepo = userRepo1;
    this.reviewRepo = reviewRepo1;
  }



  /**
   * Get all courses associated with the authenticated user.
   *
   * @return the list of courses a user is associated with.
   */
  @Operation(summary = "Get all user-courses for a user",
      description = "Retrieves all user-courses associated with a user ID", security = {})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courses found", content =
      @Content(schema = @Schema(implementation = UserCourse.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No courses found")
  })
  @SecuredEndpoint
  @GetMapping("/userCourses/user")
  public ResponseEntity<List<UserCourse>> getAll() {
    long uid = SecurityUtils.getAuthenticatedUserId();
    List<UserCourse> userCourseList = userCoursesRepo.getAllByUser_Id(uid);
    int status = 404;
    if (!userCourseList.isEmpty()) {
      logger.info("Fetching all courses for user with id: {}", uid);
      status = 200;
    } else {
      logger.error("No courses found for user with id: {}", uid);
    }
    return ResponseEntity.status(status).body(userCourseList);

  }


  /**
   * Get all user-courses associated with a course.
   *
   * @param cid the course to get by
   * @return the list of courses a course is associated with.
   */
  @Operation(summary = "Get all user-courses associated with a course",
      description = "Retrieves all users-courses associated  with a course ID" ,security = @SecurityRequirement(name = ""))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users found",
          content = @Content(schema = @Schema(implementation = UserCourse.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No users found")
  })
  @SecuredEndpoint
  @GetMapping("/userCourses/course/{cid}")
  public ResponseEntity<List<UserCourse>> getAllByCourse(@PathVariable long cid) {
    List<UserCourse> userCourseList = userCoursesRepo.getAllByCourse_Id(cid);
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
  @Operation(summary = "Get average rating for a course",
      description = "Calculates the average rating of a course based on reviews")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Average calculated",
          content = @Content(schema = @Schema(implementation = Float.class))),
      @ApiResponse(responseCode = "404", description = "No reviews found"),
      @ApiResponse(responseCode = "204", description = "Invalid rating range")
  })
  @GetMapping("/userCourses/averageRating/{cid}")
  public ResponseEntity<Float> getAverageByCourse(@PathVariable long cid) {
    List<UserCourse> courses = userCoursesRepo.getAllByCourse_Id(cid);
    if (courses.isEmpty()) {
      logger.error("No courses found for course with id: {}", cid);
      return ResponseEntity.status(404).body(null);
    }
    float average = 0;
    int count = 0;
    int status = 0;
    for (UserCourse course : courses) {
      if (course.getReview() != null) {
        if (course.getReview().getRating() > 0 && course.getReview().getRating() < 6) {
          average += course.getReview().getRating();
          count++;
        }
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
    logger.info("Fetching average rating for course with id: {}", cid);
    return ResponseEntity.status(status).body(average);
  }


  /**
   * Get all user courses from the database.
   *
   * @return all user courses
   */
  @Operation(summary = "Get all user-course relationships",
      description = "Retrieves all user-course records")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Records found",
          content = @Content(schema = @Schema(implementation = UserCourse.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No records found")
  })
  @SecuredEndpoint
  @GetMapping("/userCourses")
  public ResponseEntity<Iterable<UserCourse>> getAllUserCourses() {
    logger.info("Fetching all user Courses");
    List<UserCourse> userCourseList;
    if (userCoursesRepo.findAll().isEmpty()) {
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching all user courses");
      userCourseList = userCoursesRepo.findAll();
    }
    return ResponseEntity.status(200).body(userCourseList);
  }

  /**
   * Get all reviews for a given course.
   *
   * @param cid the course id to get the reviews for
   * @return the list of reviews for the course
   */
  @Operation(summary = "Get all reviews for a course",
      description = "Retrieves all reviews associated with a course ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reviews found",
          content = @Content(schema = @Schema(implementation = UserCourse.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No reviews found")
  })
  @GetMapping("/userCourses/reviews/course/{cid}")
  public ResponseEntity<List<reducedUserCourseDto>> getAllReviewsForCourse(@PathVariable long cid) {
    List<UserCourse> userCourseList = userCoursesRepo.getAllByCourse_Id(cid);
    List<reducedUserCourseDto> userCourseListWithReviews = new ArrayList<>();
    for (UserCourse userCourse : userCourseList) {
      if (userCourse.getReview() != null) {
        reducedUserDto reducedUserDto = new reducedUserDto(
            userCourse.getUser().getId(),
            userCourse.getUser().getName(),
            userCourse.getUser().getProfilePicture()
        );
        reducedUserCourseDto userCourseDto = new reducedUserCourseDto(
            userCourse.getId(),
            reducedUserDto,
            userCourse.getCourse(),
            userCourse.getReview(),
            userCourse.getTimestamp()
        );
        userCourseListWithReviews.add(userCourseDto);
      }
    }
    return ResponseEntity.status(200).body(userCourseListWithReviews);
  }


  /**
   * Get the last ten user courses reviews added to the database.
   *
   * @return the last ten user courses reviews
   */
  @SecuredEndpoint
  @Operation(summary = "Get latest 10 reviews",
      description = "Retrieves the 10 most recent user-course reviews")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "10 user-courses reviews found",
          content = @Content(schema = @Schema(implementation = UserCourse.class, type = "array"))),
      @ApiResponse(responseCode = "404", description = "No user-courses reviews found")
  })
  @GetMapping("/userCourses/lastUserCourses")
  public ResponseEntity<Iterable<UserCourse>> getLastTenUserCourses() {
    logger.info("Fetching the ten last user courses reviews");
    List<UserCourse> userCourseList = userCoursesRepo.findAll();
    if (userCourseList.isEmpty()) {
      logger.error("No user courses found");
      return ResponseEntity.status(404).body(null);
    } else {
      userCourseList = userCourseList.stream()
          .filter(userCourse -> userCourse.getReview() != null)
          .sorted((a, b) ->
              b.getReview().getDate().compareTo(a.getReview().getDate()))
          .toList();
    }
    userCourseList.subList(0, Math.min(10, userCourseList.size()));
    logger.info("Successfully fetched the ten last user courses reviews");
    return ResponseEntity.status(200).body(userCourseList);
  }


  /**
   * Adds a new user course to the database.
   *
   * @param cid the course id
   * @return a response entity with the status of the operation
   */
  @Operation(summary = "Enroll user in a course",
      description = "Creates or updates a user-course relationship")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Enrollment successful"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @SecuredEndpoint
  @PostMapping("/userCourses/add/{cid}")
  public ResponseEntity<String> addNewUserCourse(
      @PathVariable long cid) {
    UserCourse userCourse;
    long uid = SecurityUtils.getAuthenticatedUserId();
    if (userCoursesRepo.existsByUser_IdAndCourse_Id(uid, cid)) {
      userCourse = userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(uid, cid);
      userCourse.setTimestamp();

    } else {
      userCourse = new UserCourse();
      userCourse.setCourse(courseRepo.getCoursesById(cid));
      userCourse.setUser(userRepo.getUsersById(uid));
    }
    this.userCoursesRepo.save(userCourse);
    if (userCoursesRepo.existsById(userCourse.getId())) {
      logger.info("User course with id {} saved", userCourse.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(
          "User course with id " + userCourse.getId() + " saved");
    } else {
      logger.error("Failed to save user course with id {}", userCourse.getId());
      return ResponseEntity.status(400).build();
    }
  }


  /**
   * Delete a user from the user course database.
   * <br/>
   * <p>
   * Mainly used to clean up postman testing
   */
  @Operation(summary = "Delete user-course relationship",
      description = "Deletes a user-course relationship by user ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User-course relationship deleted"),
      @ApiResponse(responseCode = "404", description = "User-course relationship not found")
  })
  @SecuredEndpoint
  @Transactional
  @DeleteMapping("/userCourses/user/{uid}")
  public ResponseEntity<Void> deleteByUser(@PathVariable long uid) {
    if (!userCoursesRepo.existsUserCourseByUser_Id(uid)) {
      return ResponseEntity.status(404).build();
    }
    userCoursesRepo.deleteAllByUser_Id(uid);
    return ResponseEntity.status(200).build();
  }


  /**
   * Adds a new rating to a user course.
   *
   * @param review the review to add/replace in the user course
   * @param cid    the course id to add the review to
   * @return a response entity with the status of the operation.
   */
  @Operation(summary = "Add/update a review",
      description = "Adds or replaces a review for a user-course relationship")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Review updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @SecuredEndpoint
  @Transactional
  @PutMapping("/userCourses/addRating/{cid}")
  public ResponseEntity<Review> addRating(@RequestBody Review review,
                                          @PathVariable long cid) {
    if (review == null) {
      logger.error("Review object is null");
      return ResponseEntity.status(400).build();
    }
    long uid = SecurityUtils.getAuthenticatedUserId();
    // Makes sure the minimum review is 1 one star.
    if (review.getRating() < 1) {
      review.setRating(1);
    }
    review.setDate();

    UserCourse userCourse = userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(uid, cid);
    if (userCourse == null) {
      logger.error("User course not found");
      return ResponseEntity.status(404).build();
    }
    if (userCourse.getReview() != null) {
      reviewRepo.delete(userCourse.getReview());
    }
    userCourse.setReview(review);
    userCoursesRepo.save(userCourse);
    review = reviewRepo.save(review);
    logger.info("Added review with id {} to user course with id {}", review.getId(),
        userCourse.getId());
    return ResponseEntity.status(200).body(review);

  }

    /**
     * Removes a rating from a user course.
     *
     * @param cid the course id to remove the review from
     * @return a response entity with the status of the operation
     */
    @Operation(summary = "Remove a review",
        description = "Removes a review from a user-course relationship")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review removed"),
        @ApiResponse(responseCode = "404", description = "User-course relationship not found")
    })
    @SecuredEndpoint
    @Transactional
    @DeleteMapping("/userCourses/removeRating/{cid}")
  public ResponseEntity<Review> removeRating(@PathVariable long cid) {
    long uid = SecurityUtils.getAuthenticatedUserId();
    UserCourse userCourse = userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(uid, cid);
    if (userCourse == null) {
      logger.error("User course not found");
      return ResponseEntity.status(404).build();
    }
    if (userCourse.getReview() != null) {
      reviewRepo.delete(userCourse.getReview());
      userCourse.setReview(null);
      userCoursesRepo.save(userCourse);
      logger.info("Removed review from user course with id {}", userCourse.getId());
      return ResponseEntity.status(200).build();
    } else {
      logger.error("No review found for user course with id {}", userCourse.getId());
      return ResponseEntity.status(404).build();
    }
  }

  @Operation(summary = "Check user enrollment",
      description = "Checks if a user is enrolled in a course")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User is enrolled",
          content = @Content(schema = @Schema(implementation = Boolean.class))),
      @ApiResponse(responseCode = "404", description = "User is not enrolled")
  })
  /**
   * Checks if a user is enrolled in a course.
   *
   * @param uid the user id
   * @param cid the course id
   * @return true if the user is enrolled, false otherwise
   */
  @SecuredEndpoint
  @GetMapping("/userCourses/enrolled/{cid}")
  public ResponseEntity<Boolean> isUserEnrolledInCourse(
      @PathVariable long cid) {
    long uid = SecurityUtils.getAuthenticatedUserId();
    UserCourse userCourse = userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(uid, cid);
    if (userCourse != null) {
      logger.info("User with id {} is enrolled in course with id {}", uid, cid);
      return ResponseEntity.status(200).body(true);
    } else {
      logger.error("User with id {} is not enrolled in course with id {}", uid, cid);
      return ResponseEntity.status(404).body(false);
    }
  }

  private class reducedUserCourseDto {
    private long id;
    private reducedUserDto user;
    private Course course;
    private Review review;
    private Timestamp timestamp;

    public reducedUserCourseDto(long id, reducedUserDto user, Course course, Review review,
                                Timestamp timestamp) {
      this.id = id;
      this.user = user;
      this.course = course;
      this.review = review;
      this.timestamp = timestamp;
    }

    public long getId() {
      return id;
    }
    public Review getReview() {
      return review;
    }

    public Course getCourse() {
      return course;
    }

    public reducedUserDto getUser() {
      return user;
    }
    public Timestamp getTimestamp() {
      return timestamp;
    }

  }

  private class reducedUserDto {
    private long id;
    private String name;
    private String profilePicture;

    public reducedUserDto(long id, String name, String profilePicture) {
      this.id = id;
      this.name = name;
      this.profilePicture = profilePicture;
    }

    public String getName() {
      return name;
    }

    public String getProfilePicture() {
      return profilePicture;
    }

    public long getId() {
      return id;
    }
  }

}
