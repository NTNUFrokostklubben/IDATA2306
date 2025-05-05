package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Keywords;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.KeywordsRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling keywords.
 */
@RestController
public class KeywordsController {
  private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
  private final KeywordsRepo keywordsRepo;
  private final CourseRepo courseRepo;

  /**
   * Constructor for KeywordsController.
   *
   * @param keywordsRepo the keyword repository
   * @param courseRepo the course repository
   * @param userRepo the user repository
   */
  @Autowired
  public KeywordsController(KeywordsRepo keywordsRepo, CourseRepo courseRepo, UserRepo userRepo) {
    this.keywordsRepo = keywordsRepo;
    this.courseRepo = courseRepo;
  }

  /**
   * Retrieves all keywords for a specific course by its course ID.
   */
  @GetMapping("/keyword/{cid}")
  public ResponseEntity<List<Keywords>> getKeyword(@PathVariable long cid) {
    logger.info("Fetching keywords with CourseId: {}", cid);
    List<Keywords> keyword = keywordsRepo.getAllByCourse_Id(cid);
    if (keyword.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    return ResponseEntity.status(200).body(keyword);
  }
}
