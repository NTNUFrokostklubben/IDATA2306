package no.ntnu.learniverseconnect.controllers;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceDto;
import no.ntnu.learniverseconnect.model.dto.SearchFilterDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.courseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.KeywordsRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling search requests.
 */
@RestController
public class SearchController {

  private final CourseRepo courseRepo;
  private final courseProviderRepo courseProviderController;
  private final OfferableCoursesRepo offerableCoursesController;
  private final KeywordsRepo keywordsController;
  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);


  /**
   * Constructor for SearchController.
   *
   * @param courseController           Course repository
   * @param courseProviderController   Course provider repository
   * @param offerableCoursesController Offerable courses repository
   * @param keywordsController         Keywords repository
   */
  public SearchController(CourseRepo courseController,
                          courseProviderRepo courseProviderController,
                          OfferableCoursesRepo offerableCoursesController,
                          KeywordsRepo keywordsController) {
    this.courseRepo = courseController;
    this.courseProviderController = courseProviderController;
    this.offerableCoursesController = offerableCoursesController;
    this.keywordsController = keywordsController;
  }


  /**
   * Searches for courses based on the provided search filter.
   *
   * @param searchFilterDto
   * @return
   */
  @PostMapping("/search")
  public ResponseEntity<List<CourseWithMinPriceDto>> searchCourses(
      @RequestBody SearchFilterDto searchFilterDto) {
    logger.info("Searching for courses with filter: {}", searchFilterDto);

    List<CourseWithMinPriceDto> courses = new ArrayList<>();



//    courses = offerableCoursesController.findVisibleCoursesWithMinPriceWhereClosestDateBetweenContainingCategoryContainingDifficultyLevelWithinCreditsWithinPriceRange(
//        searchFilterDto.getSearchValue(),
//        searchFilterDto.getDateRange().getStartDate(),
//        searchFilterDto.getDateRange().getEndDate(),
//        searchFilterDto.getCategories(),
//        searchFilterDto.getDiffLevels(),
//        searchFilterDto.getCourseSizeRange().getMinCredits(),
//        searchFilterDto.getCourseSizeRange().getMaxCredits()
//        );

    courses = offerableCoursesController.superFilter(
        searchFilterDto.getSearchValue(),
        searchFilterDto.getDateRange().getStartDate(),
        searchFilterDto.getDateRange().getEndDate(),
        searchFilterDto.getCategories(),
        searchFilterDto.getDiffLevels(),
        searchFilterDto.getRatingRange().getMinRating(),
        searchFilterDto.getRatingRange().getMaxRating(),
        searchFilterDto.getCourseSizeRange().getMinCredits(),
        searchFilterDto.getCourseSizeRange().getMaxCredits(),
        searchFilterDto.getPriceRange().getMinPrice(),
        searchFilterDto.getPriceRange().getMaxPrice()
    );


    return ResponseEntity.status(200).body(courses);
  }


}
