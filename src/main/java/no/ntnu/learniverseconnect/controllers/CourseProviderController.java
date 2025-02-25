package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseProviderController {

  CourseProviderRepo repo;

  @Autowired
  public CourseProviderController(CourseProviderRepo repo) {
    this.repo = repo;
  }

  /**
   * Returns a list of all course providers.
   * @return a list of all course providers.
   */
  @GetMapping("/providers")
    public ResponseEntity<List<CourseProvider>> getProviders() {
        return ResponseEntity.status(200).body(repo.findAll());
    };
  /**
   * Returns a course provider with the given id.
   * @param id the id of the course provider to return.
   * @return the course provider with the given id.
   */
  @GetMapping("/provider/{id}")
    public ResponseEntity<CourseProvider> getProvider(@PathVariable int id) {
        return ResponseEntity.status(200).body(repo.findById(id).orElse(null));
    };

    /**
     * Adds a course provider to the database.
     * @param provider the course provider to add.
     */
  @PostMapping("/provider")
    public void addProvider(@RequestBody CourseProvider provider) {
        repo.save(provider);
    };
}
