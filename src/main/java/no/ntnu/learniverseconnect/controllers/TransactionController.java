package no.ntnu.learniverseconnect.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.learniverseconnect.model.dto.CourseProviderStatsDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.entities.Transaction;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.TransactionRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public TransactionController(TransactionRepo transactionRepo, CourseRepo courseRepo,
      OfferableCoursesRepo offerableCoursesRepo, UserRepo userRepo, CourseProviderRepo courseProviderRepo ) {
    this.repo = transactionRepo;
    this.courseRepo = courseRepo;
    this.offerableCoursesRepo = offerableCoursesRepo;
    this.userRepo = userRepo;
    this.courseProviderRepo = courseProviderRepo;

  }

  /**
   * Returns a list of all transactions.
   *
   * @return a list of all transactions.
   */
  @GetMapping("/transactions")
  public ResponseEntity<List<Transaction>> getTransactions() {
    return ResponseEntity.status(200).body(repo.findAll());
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
      return ResponseEntity.status(200).body(transaction);
    } else {
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
        return ResponseEntity.status(200).body(transactions);
        } else {
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
      return ResponseEntity.status(200).body(transactions);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }


  /**
   * Returns a list of transactions for a specific user and course.
   *
   * @param userId the ID of the user to retrieve transactions for.
   * @param courseId the ID of the course to retrieve transactions for.
   * @return a list of transactions for the specified user and course.
   */
  @GetMapping("/transaction/user/{userId}/course/{courseId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserIdAndCourseId(
        @PathVariable long userId, @PathVariable long courseId) {

        Course course = courseRepo.getCoursesById(courseId);
        List<Transaction> transactions = repo.findAllByUser_IdAndOfferableCourses_Course(userId, course);
        if (transactions != null && !transactions.isEmpty()) {
        return ResponseEntity.status(200).body(transactions);
        } else {
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
        return ResponseEntity.status(400).body(null);
    }
        if (transaction.getUser() == null || transaction.getOfferableCourses() == null) {
            return ResponseEntity.status(400).body(null);
    }
        repo.save(transaction);
        return ResponseEntity.status(201).body(transaction);
    }

    /**
     * Adds a new transaction based on offerable course ID and user ID.
     *
     * @param oId the ID of the offerable course.
     * @param uId the ID of the user.
     * @return the added transaction.
     */

    @PostMapping("/transaction/offerId/{oId}/userid/{uId}")
  public ResponseEntity<String> addTransaction(@PathVariable long oId, @PathVariable long uId){
    Transaction transaction = new Transaction();
      OfferableCourses offerableCourse = offerableCoursesRepo.getOfferableCoursesById(oId);
      User user =  userRepo.getUsersById((uId));
      if (offerableCourse == null || user == null) {
        return ResponseEntity.status(404).body(null);
      }
    transaction.setOfferableCourses(offerableCourse);
    transaction.setUser(user);
    transaction.setPricePaid(offerableCourse.getPrice()* (1- offerableCourse.getDiscount()));
    repo.save(transaction);
    return ResponseEntity.status(201).body("ok");
  }

  /**
   * Finds the revenue for each provider
   *
   * @return the revenue for each provider
   */
  @GetMapping("/transaction/providersStats")
  public ResponseEntity<List<CourseProviderStatsDto>> getProviderStats(){
    List<CourseProviderStatsDto> statsList = new ArrayList<>();
    List<CourseProvider> providers = courseProviderRepo.findAll();
    for (CourseProvider provider : providers ){
      List<Transaction> transactions = repo.findAllByOfferableCourses_Provider(provider);
      float revenueSum = 0;
      for (Transaction transaction : transactions){
        revenueSum+=transaction.getPricePaid();
      }
      CourseProviderStatsDto providerStats = new CourseProviderStatsDto(provider.getId(), provider.getName(), revenueSum);
      statsList.add(providerStats);
    }
    if (statsList.isEmpty()){
      return ResponseEntity.status(404).body(null);
    } else {
      return ResponseEntity.status(200).body(statsList);
    }
  }

  /**
   * Find the total revenue
   *
   * @return the total revenue
   */
  @GetMapping("/transaction/totalRevenue")
  public ResponseEntity<Float> getTotalRevenue(){
    List<Transaction> transactions = repo.findAll();
    float revenueSum = 0;
    for (Transaction transaction : transactions){
      revenueSum+=transaction.getPricePaid();
    }
    return ResponseEntity.status(200).body(revenueSum);
  }

  /**
   * Find the average revenue per course
   *
   * @Return the average revenue per course
   */
  @GetMapping("/transaction/averageRevenuePerCourse")
  public ResponseEntity<Float> getAvgRevenuePerCourse(){
    List<Course> courses = courseRepo.findAll();
    int totalCourses = courses.size();
    List<Transaction> transactions = repo.findAll();
    float revenueSum = 0;
    for (Transaction transaction : transactions){
      revenueSum+=transaction.getPricePaid();
    }
    Float avgRevenue = revenueSum/totalCourses;
    return ResponseEntity.status(200).body(avgRevenue);
  }

  /**
   * Get the revenue for the last 30 days
   * 
   * @return the revenue for the last 30 days
   */
  @GetMapping("/transaction/revenueLast30Days")
  public ResponseEntity<Float> getRevenueLast30Days(){
    List<Transaction> transactions = repo.findAll();
    float revenueSum = 0;
    for (Transaction transaction : transactions){
      if (transaction.getTimeOfTransaction().after(
          Timestamp.valueOf(LocalDateTime.now().minusDays(30)))){
        revenueSum+=transaction.getPricePaid();
      }
    }
    return ResponseEntity.status(200).body(revenueSum);
  }

}
