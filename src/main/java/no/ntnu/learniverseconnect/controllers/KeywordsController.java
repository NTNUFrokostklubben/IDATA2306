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

@RestController
public class KeywordsController {
  private final KeywordsRepo keywordsRepo;
  private final CourseRepo courseRepo;
  private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);

  @Autowired
  public KeywordsController(KeywordsRepo keywordsRepo, CourseRepo courseRepo, UserRepo userRepo) {
    this.keywordsRepo = keywordsRepo;
    this.courseRepo = courseRepo;
  }

  @GetMapping("/keyword/{id}")
  public ResponseEntity<Keywords> getKeyword(@PathVariable long id) {
    logger.info("Fetching keyword with CourseId: {}", id);
    Keywords keyword = keywordsRepo.getKeywordsById(id);
    return ResponseEntity.status(200).body(keyword);
  }
}
