package no.ntnu.learniverseconnect.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import no.ntnu.learniverseconnect.model.dto.CourseProviderStatsDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.entities.Transaction;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.TransactionRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling transaction-related requests.
 */
@RestController
public class TransactionController {

  private final TransactionRepo repo;
  private final CourseRepo courseRepo;
  private final OfferableCoursesRepo offerableCoursesRepo;
  private final UserRepo userRepo;
  private final CourseProviderRepo courseProviderRepo;
  private final UserCoursesRepo userCoursesRepo;
  private final Logger logger = Logger.getLogger(TransactionController.class.getName());

  /**
   * Constructor for TransactionController.
   *
   * @param transactionRepo the transaction repository
   * @param courseRepo the course repository
   * @param offerableCoursesRepo the offerable courses repository
   * @param userRepo the user repository
   * @param courseProviderRepo the course provider repository
   */
  @Autowired
  public TransactionController(TransactionRepo transactionRepo, CourseRepo courseRepo,
                               OfferableCoursesRepo offerableCoursesRepo, UserRepo userRepo,
                               CourseProviderRepo courseProviderRepo, UserCoursesRepo userCoursesRepo) {
    this.repo = transactionRepo;
    this.courseRepo = courseRepo;
    this.offerableCoursesRepo = offerableCoursesRepo;
    this.userRepo = userRepo;
    this.courseProviderRepo = courseProviderRepo;
    this.userCoursesRepo = userCoursesRepo;
  }

  /**
   * Returns a list of all transactions.
   *
   * @return a list of all transactions.
   */
  @GetMapping("/transactions")
  public ResponseEntity<List<Transaction>> getTransactions() {
    List<Transaction> transactions = repo.findAll();
    if (transactions == null || transactions.isEmpty()) {
      logger.warning("No transactions found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Transactions found: " + transactions.size());
      return ResponseEntity.status(200).body(transactions);
    }
  }

  /**
   * Returns a transaction by its ID.
   *
   * @param id the ID of the transaction to retrieve.
   * @return the transaction with the specified ID, or null if not found.
   */
  @GetMapping("/transaction/{id}")
  public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) {
    Transaction transaction = repo.findById(id);
    if (transaction != null) {
      logger.info("Transaction found with id: " + id);
      return ResponseEntity.status(200).body(transaction);
    } else {
      logger.warning("No transactions found with id: " + id);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Returns a list of transactions for a specific user.
   *
   * @param userId the ID of the user to retrieve transactions for.
   * @return a list of transactions for the specified user.
   */
  @GetMapping("/transaction/user/{userId}")
  public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable long userId) {
    List<Transaction> transactions = repo.findAllByUser_Id(userId);
    if (transactions != null && !transactions.isEmpty()) {
      logger.info("Transactions found for user with id: " + userId);
      return ResponseEntity.status(200).body(transactions);
    } else {
      logger.warning("No transactions found for user with id: " + userId);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Returns a list of transactions for a specific course.
   *
   * @param courseId the ID of the course to retrieve transactions for.
   * @return a list of transactions for the specified course.
   */
  @GetMapping("/transaction/course/{courseId}")
  public ResponseEntity<List<Transaction>> getTransactionsByCourseId(@PathVariable long courseId) {
    Course course = courseRepo.getCoursesById(courseId);
    List<Transaction> transactions = repo.findAllByOfferableCourses_Course(course);
    if (transactions != null && !transactions.isEmpty()) {
      logger.info("Transactions found for course with id: " + courseId);
      return ResponseEntity.status(200).body(transactions);
    } else {
      logger.warning("No transactions found for course with id: " + courseId);
      return ResponseEntity.status(404).body(null);
    }
  }


  /**
   * Returns a list of transactions for a specific user and course.
   *
   * @param userId   the ID of the user to retrieve transactions for.
   * @param courseId the ID of the course to retrieve transactions for.
   * @return a list of transactions for the specified user and course.
   */
  @GetMapping("/transaction/user/{userId}/course/{courseId}")
  public ResponseEntity<List<Transaction>> getTransactionsByUserIdAndCourseId(
      @PathVariable long userId, @PathVariable long courseId) {

    Course course = courseRepo.getCoursesById(courseId);
    List<Transaction> transactions = repo.findAllByUser_IdAndOfferableCourses_Course(userId,
                                                                                     course);
    if (transactions != null && !transactions.isEmpty()) {
      logger.info("Transactions found for user with id: " + userId + " and course with id: "
          + courseId);
      return ResponseEntity.status(200).body(transactions);
    } else {
      logger.warning("No transactions found for user with id: " + userId + " and course with id: "
          + courseId);
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Adds a new transaction.
   *
   * @param transaction the transaction to add.
   * @return the added transaction.
   */

  @PostMapping("/transaction")
  public ResponseEntity<Transaction> addTransaction(@PathVariable Transaction transaction) {
    if (transaction == null) {
      logger.warning("Transaction is null");
      return ResponseEntity.status(400).body(null);
    }
    if (transaction.getUser() == null || transaction.getOfferableCourses() == null) {
      logger.warning("Transaction is missing user or offerable course");
      return ResponseEntity.status(400).body(null);
    }
    repo.save(transaction);
    logger.info("Transaction added with id: " + transaction.getId());
    return ResponseEntity.status(201).body(transaction);
  }

  /**
   * Adds a new transaction based on offerable course ID and user ID.
   * Adds the transaction to the database and updates or adds the corresponding user course
   * to the database.
   *
   * @param oId the ID of the offerable course.
   * @param uId the ID of the user.
   * @return the added transaction.
   */
  @PostMapping("/transaction/offerId/{oId}/userid/{uId}")
  public ResponseEntity<String> addTransaction(@PathVariable long oId, @PathVariable long uId) {
    Transaction transaction = new Transaction();
    OfferableCourses offerableCourse = offerableCoursesRepo.getOfferableCoursesById(oId);
    User user = userRepo.getUsersById((uId));
    if (offerableCourse == null || user == null) {
      return ResponseEntity.status(404).body(null);
    }
    // Add the transaction to the database
    transaction.setOfferableCourses(offerableCourse);
    transaction.setUser(user);
    transaction.setTimeOfTransaction(new Timestamp(System.currentTimeMillis()));
    transaction.setPricePaid(offerableCourse.getPrice() * (1 - offerableCourse.getDiscount()));
     repo.save(transaction);
    // TODO add guard?

    // Add the user course to the database after transaction is created;
    UserCourse userCourse;
    if (userCoursesRepo.existsByUser_IdAndCourse_Id(uId, offerableCourse.getCourse().getId())) {
      userCourse =  userCoursesRepo.getUserCoursesByUser_IdAndCourse_Id(uId, offerableCourse.getCourse().getId());
      userCourse.setTimestamp();
      // If the user course doesn't exist, create a new one
    } else {
      userCourse = new UserCourse();
      userCourse.setCourse(courseRepo.getCoursesById(offerableCourse.getCourse().getId()));
      userCourse.setUser(userRepo.getUsersById(uId));
    }
    this.userCoursesRepo.save(userCourse);
    if (userCoursesRepo.existsById(userCourse.getId())) {
      return ResponseEntity.status(HttpStatus.CREATED).body(
          "transaction with course id " + userCourse.getId() + "and  offerable course id " +offerableCourse.getCourse().getId() + " saved");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Finds the revenue for each provider.
   *
   * @return the revenue for each provider
   */
  @GetMapping("/transaction/providersStats")
  public ResponseEntity<List<CourseProviderStatsDto>> getProviderStats() {
    List<CourseProviderStatsDto> statsList = new ArrayList<>();
    List<CourseProvider> providers = courseProviderRepo.findAll();
    for (CourseProvider provider : providers) {
      List<Transaction> transactions = repo.findAllByOfferableCourses_Provider(provider);
      float revenueSum = 0;
      for (Transaction transaction : transactions) {
        revenueSum += transaction.getPricePaid();
      }
      CourseProviderStatsDto providerStats =
          new CourseProviderStatsDto(provider.getId(), provider.getName(), revenueSum);
      statsList.add(providerStats);
    }
    if (statsList.isEmpty()) {
      logger.warning("No providers found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Providers found: " + statsList.size());
      return ResponseEntity.status(200).body(statsList);
    }
  }

  /**
   * Find the total revenue.
   *
   * @return the total revenue
   */
  @GetMapping("/transaction/totalRevenue")
  public ResponseEntity<Float> getTotalRevenue() {
    List<Transaction> transactions = repo.findAll();
    if (transactions.isEmpty()) {
      logger.warning("No transactions found");
      return ResponseEntity.status(204).body(null);
    }
    float revenueSum = 0;
    for (Transaction transaction : transactions) {
      revenueSum += transaction.getPricePaid();
    }
    logger.info("Total revenue: " + revenueSum);
    return ResponseEntity.status(200).body(revenueSum);
  }

  /**
   * Find the average revenue per course.
   *
   * @Return the average revenue per course
   */
  @GetMapping("/transaction/averageRevenuePerCourse")
  public ResponseEntity<Float> getAvgRevenuePerCourse() {
    List<Course> courses = courseRepo.findAll();
    int totalCourses = courses.size();
    List<Transaction> transactions = repo.findAll();

    if (transactions.isEmpty()) {
      logger.warning("No transactions found");
      return ResponseEntity.status(404).body(null);
    } else if (courses.isEmpty()) {
      logger.warning("No courses found");
      return ResponseEntity.status(404).body(null);
    }

    float revenueSum = 0;
    for (Transaction transaction : transactions) {
      revenueSum += transaction.getPricePaid();
    }
    Float avgRevenue = revenueSum / totalCourses;
    logger.info("Average revenue per course: " + avgRevenue);
    return ResponseEntity.status(200).body(avgRevenue);
  }

  /**
   * Get the revenue for the last 30 days.
   *
   * @return the revenue for the last 30 days
   */
  @GetMapping("/transaction/revenueLast30Days")
  public ResponseEntity<Float> getRevenueLast30Days() {
    List<Transaction> transactions = repo.findAll();

    if (transactions.isEmpty()) {
      logger.warning("No transactions found");
      return ResponseEntity.status(404).body(null);
    }
    float revenueSum = 0;
    for (Transaction transaction : transactions) {
      if (transaction.getTimeOfTransaction().after(
          Timestamp.valueOf(LocalDateTime.now().minusDays(30)))) {
        revenueSum += transaction.getPricePaid();
      }
    }
    return ResponseEntity.status(200).body(revenueSum);
  }

}
