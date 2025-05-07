package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Review;
import no.ntnu.learniverseconnect.model.repos.ReviewRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

  private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
  private final ReviewRepo reviewRepo;

  @Autowired
  public ReviewController(ReviewRepo reviewRepo) {
    this.reviewRepo = reviewRepo;
  }

  /**
   * Returns a list of all reviews.
   *
   * @return a list of all reviews.
   */
  @GetMapping("/reviews")
  public ResponseEntity<List<Review>> getReviews() {
    List<Review> reviews = reviewRepo.findAll();
    if (reviews.isEmpty()) {
      logger.warn("No reviews found");
      return ResponseEntity.status(404).body(null);
    } else {
      logger.info("Fetching all reviews");
      return ResponseEntity.status(200).body(reviews);
    }
  }
}