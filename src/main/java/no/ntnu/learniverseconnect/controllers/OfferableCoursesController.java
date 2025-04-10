package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.CourseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferableCoursesController {

  private OfferableCoursesRepo repo;

  @Autowired
  public OfferableCoursesController(OfferableCoursesRepo repo) {
    this.repo = repo;
  }

  /**
   * Returns a list of all offerable courses with provider information.
   *
   * @return a list of all offerable courses.
   */
  @GetMapping("/offerableCourses")
  public ResponseEntity<List<OfferableCourses>> getOfferableCourses() {
    return ResponseEntity.status(200).body(repo.findAll());
  }


  /**
   * Returns a list of offerable courses for a given course id.
   *
   * @param cid Course id.
   * @return List of offerable courses for the given course id.
   */
  @GetMapping("/offerableCourses/course/{cid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByCourseId(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);
    if (!list.isEmpty()) {
      return ResponseEntity.status(200).body(list);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }


  /**
   */
  @GetMapping("/offerableCourses/coursePrice/{cid}")
  public ResponseEntity<Float> getOfferablePriceByCourseId(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);

    float lowestPrice;
    if (list.isEmpty()){
      return ResponseEntity.status(404).body(null);
    } else {
      lowestPrice = list.get(0).getPrice();
    }

    for (OfferableCourses oc : list) {
      if (lowestPrice > oc.getPrice()){
        lowestPrice = oc.getPrice();
      }
      if (lowestPrice > oc.getDiscount()) {
        lowestPrice = oc.getDiscount();
      }
    }
    return ResponseEntity.status(200).body(lowestPrice);


  }

  /**
   * Returns a offerable course for a given id.
   *
   * @param cid Course id.
   * @return Offerable course for the given course id.
   */
  @GetMapping("/offerableCourses/{cid}")
  public ResponseEntity<OfferableCourses> getOfferableCoursesById(@PathVariable long cid) {
    OfferableCourses offerable = repo.getOfferableCoursesById(cid);
    if (offerable != null) {
      return ResponseEntity.status(200).body(offerable);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Returns a list of offerable courses for a given provider id.
   *
   * @param pid Provider id.
   * @return List of offerable courses for the given provider id.
   */
  @GetMapping("/offerableCourses/provider/{pid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByProviderId(@PathVariable long pid) {
    List<OfferableCourses> list = repo.getAllByProvider_Id(pid);
    if (!list.isEmpty()) {
      return ResponseEntity.status(200).body(list);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Adds an offerable course to the database.
   *
   * @param offerableCourse the offerable course to add.
   */
  @PostMapping("/offerableCourse")
  public ResponseEntity<OfferableCourses> addOfferableCourse(@RequestBody OfferableCourses offerableCourse) {
    repo.save(offerableCourse);
    return ResponseEntity.status(201).body(offerableCourse);
  }

}
