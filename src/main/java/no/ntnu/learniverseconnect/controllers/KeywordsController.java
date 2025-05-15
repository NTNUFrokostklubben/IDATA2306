package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Keywords;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.KeywordsRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * @param courseRepo   the course repository
   * @param userRepo     the user repository
   */
  @Autowired
  public KeywordsController(KeywordsRepo keywordsRepo, CourseRepo courseRepo, UserRepo userRepo) {
    this.keywordsRepo = keywordsRepo;
    this.courseRepo = courseRepo;
  }

  /**
   * Retrieves all keywords for a specific course by its course ID.
   * Uses minimal DTO for returning keyword data.
   */
  @Operation(
      summary = "Get keywords by course ID",
      description = "Retrieves all keywords associated with a specific course ID"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "List of keywords for the specified course ID"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No keywords found for the specified course ID"
      )
  })
  @GetMapping("/keyword/{cid}")
  public ResponseEntity<List<KeywordsDTO>> getKeyword(@PathVariable long cid) {
    logger.info("Fetching keywords with CourseId: {}", cid);
    List<Keywords> keyword = keywordsRepo.getAllByCourse_Id(cid);
    List<KeywordsDTO> keywordsDTOList = keyword
        .stream()
        .map(keywords1 -> {
          KeywordsDTO keywordsDTO = new KeywordsDTO();
          keywordsDTO.keyword = keywords1.getKeyword();
          return keywordsDTO;
        })
        .toList();

    if (keywordsDTOList.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    return ResponseEntity.status(200).body(keywordsDTOList);
  }

  /**
   * This method takes in a list of keywords and then replaces all keywords in the database with
   * the new list of keywords.
   * <p>
   * THIS IS DESTRUCTIVE! Make sure it is all right to delete all keywords.
   *
   * @param cid course ID
   * @param keywords keywords to replace with (array of strings)
   * @return ResponseEntity with the updated list of keywords
   */
  @Operation(
      summary = "Replaces keywords",
      description = "Replaces all keywords in the database with new ones. DESTRUCTIVE METHOD!"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "List of new keywords replacing the old"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No keywords provided"
      )
  })
  @PostMapping("/keyword/{cid}")
  public ResponseEntity<List<KeywordsDTO>> addKeyword(@PathVariable Long cid,
                                             @RequestBody String[] keywords) {
    logger.info("Adding keywords to course with ID: {}", cid);
    List<Keywords> existingKeywords = keywordsRepo.getAllByCourse_Id(cid);
    keywordsRepo.deleteAll(existingKeywords);
    for (String keyword : keywords) {
      Keywords newKeyword = new Keywords();
      newKeyword.setKeyword(keyword);
      newKeyword.setCourse(courseRepo.getReferenceById(cid.intValue()));
      keywordsRepo.save(newKeyword);

    }
    List<KeywordsDTO> keywordsDTOList = keywordsRepo.getAllByCourse_Id(cid)
        .stream()
        .map(keywords1 -> {
          KeywordsDTO keywordsDTO = new KeywordsDTO();
          keywordsDTO.keyword = keywords1.getKeyword();
          return keywordsDTO;
        })
        .toList();


    return ResponseEntity.status(200).body(keywordsDTOList);
  }



  /**
   * Deletes all keywords for course.
   * <br/>
   * mainly used to clean up postman tests
   * @param cid the course's id.
   * @return ResponseEntity with HTTP codes.
   */
  @Operation(summary = "Delete all keywords for a course",
      description = "Deletes all keywords associated with a specific user ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "keywords deleted"),
      @ApiResponse(responseCode = "404", description = "No keywords found for user")
  })
  @Transactional
  @DeleteMapping("/keywords/course/{cid}")
  public ResponseEntity<Void> deleteTransactionOnUser(@PathVariable long cid){
    if(!keywordsRepo.existsByCourse_Id(cid)){
      return ResponseEntity.status(404).build();
    }
    keywordsRepo.deleteAllByCourse_Id(cid);
    return ResponseEntity.status(200).build();
  }

  /**
   * Minimal keyword DTO for returning keyword data.
   */
  private static class KeywordsDTO {
    private String keyword;
    public String getKeyword() {
      return keyword;
    }
    public void setKeyword(String keyword) {
      this.keyword = keyword;
    }
  }

}
