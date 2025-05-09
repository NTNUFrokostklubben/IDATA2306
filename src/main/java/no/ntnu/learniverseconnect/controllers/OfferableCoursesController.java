package no.ntnu.learniverseconnect.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceAndRatingDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for offerable courses.
 */
@RestController
public class OfferableCoursesController {

  private OfferableCoursesRepo repo;
  private CourseRepo courseRepo;


  @Autowired
  public OfferableCoursesController(OfferableCoursesRepo repo, CourseRepo courseRepor) {
    this.repo = repo;
    this.courseRepo = courseRepo;
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
   * Returns an offerable course with the given id.
   *
   * @param id id of the offerable course to return.
   * @return offerable course with the given id.
   */
  @GetMapping("/offerableCourses/{id}")
  public ResponseEntity<OfferableCourses> getOfferableCourseById(@PathVariable int id) {
    OfferableCourses offerableCourse = repo.findById(id).orElse(null);
    if (offerableCourse != null) {
      return ResponseEntity.status(200).body(offerableCourse);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Deletes an offerable course with the given id.
   *
   * @param id id of the offerable course to delete.
   * @return response entity with status code 200 if the course was deleted, 404 if not found.
   */
  @DeleteMapping("/offerableCourses/{id}")
  public ResponseEntity<Void> deleteOfferableCourse(@PathVariable long id) {
    OfferableCourses offerableCourse = repo.getOfferableCoursesById(id);
    if (offerableCourse != null) {
      repo.delete(offerableCourse);
      return ResponseEntity.status(200).build();
    } else {
      return ResponseEntity.status(404).build();
    }
  }


  /**
   * Returns a list of offerable courses for a given course id.
   *
   * @param cid Course id.
   * @return List of offerable courses for the given course id.
   */
  @GetMapping("/offerableCourses/course/{cid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByCourseId(
      @PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);
    if (!list.isEmpty()) {
      return ResponseEntity.status(200).body(list);
    } else {
      return ResponseEntity.status(404).body(null);
    }
  }

  /**
   * Returns the lowest price of offerable courses for a given course id.
   */
  //TODO delete?
  @GetMapping("/offerableCourses/lowestPrice/course/{cid}")
  public ResponseEntity<Float> getOfferablePriceByCourseId(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);

    float lowestPrice;
    if (list.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    } else {
      lowestPrice = list.get(0).getPrice() * (1 - list.get(0).getDiscount());
      for (OfferableCourses oc : list) {
        float discountedPrice = oc.getPrice() * (1 - oc.getDiscount());
        if (discountedPrice < lowestPrice) {
          lowestPrice = discountedPrice;
        }
      }
    }
    return ResponseEntity.status(200).body(lowestPrice);
  }

  /**
   * Returns the lowest price of offerable courses for a given course id.
   */
  //TODO delete?
  @GetMapping("/offerableCourses/closestDate/course/{cid}")
  public ResponseEntity<Date> getOfferableClosestDateByCourseID(@PathVariable long cid) {
    List<OfferableCourses> list = repo.getAllByCourse_Id(cid);

    Date closestDate;
    if (list.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    } else {
      closestDate = list.get(0).getDate();
      for (OfferableCourses oc : list) {
        if (oc.getDate().before(closestDate)) {
          closestDate = oc.getDate();
        }
      }
    }
    return ResponseEntity.status(200).body(closestDate);
  }

  /**
   * Returns a list of offerable courses for a given provider id.
   *
   * @param pid Provider id.
   * @return List of offerable courses for the given provider id.
   */
  @GetMapping("/offerableCourses/provider/{pid}")
  public ResponseEntity<List<OfferableCourses>> getOfferableCoursesByProviderId(
      @PathVariable long pid) {
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
  public ResponseEntity<OfferableCourses> addOfferableCourse(
      @RequestBody OfferableCourses offerableCourse) {
    repo.save(offerableCourse);
    return ResponseEntity.status(201).body(offerableCourse);
  }


}
