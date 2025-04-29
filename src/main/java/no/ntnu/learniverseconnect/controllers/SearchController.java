package no.ntnu.learniverseconnect.controllers;

import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasCategory;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasCreditsBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDateBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDescription;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDiffLevel;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasPriceBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasRatingBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasTitle;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasVisibility;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceAndRatingDto;
import no.ntnu.learniverseconnect.model.dto.SearchFilterDto;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.courseProviderRepo;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling search requests.
 */
@RestController
public class SearchController {

  private final CourseRepo courseRepo;
  private final courseProviderRepo courseProviderRepo;
  private final OfferableCoursesRepo offerableCoursesRepo;
  private final UserCoursesRepo userCoursesRepo;
  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);


  /**
   * Constructor for SearchController.
   *
   * @param courseController           Course repository
   * @param courseProviderController   Course provider repository
   * @param offerableCoursesController Offerable courses repository
   * @param userCoursesRepo         UserCourses repository, for Ratings
   */
  public SearchController(CourseRepo courseController,
                          courseProviderRepo courseProviderController,
                          OfferableCoursesRepo offerableCoursesController,
                          UserCoursesRepo userCoursesRepo) {
    this.courseRepo = courseController;
    this.courseProviderRepo = courseProviderController;
    this.offerableCoursesRepo = offerableCoursesController;
    this.userCoursesRepo = userCoursesRepo;
  }


  /**
   * Searches for courses based on the provided search filter.
   *
   * @param searchFilterDto
   * @return
   */
  @PostMapping("/search")
  public ResponseEntity<List<CourseWithMinPriceAndRatingDto>> searchCourses(
      @RequestBody SearchFilterDto searchFilterDto) {
    logger.info("Searching for courses with filter: {}", searchFilterDto);


    List<OfferableCourses> courses = offerableCoursesRepo.findAll(
        hasVisibility(true)
            .and(hasDiffLevel(searchFilterDto.getDiffLevels()))
            .and(hasCategory(searchFilterDto.getCategories()))
            .and(hasCreditsBetween(
                searchFilterDto.getCourseSizeRange().getMinCredits(),
                searchFilterDto.getCourseSizeRange().getMaxCredits()
            ))
            .and(hasPriceBetween(
                searchFilterDto.getPriceRange().getMinPrice(),
                searchFilterDto.getPriceRange().getMaxPrice()
            ))
            .and(hasTitle(searchFilterDto.getSearchValue()).or(
                hasDescription(searchFilterDto.getSearchValue())))
            .and(hasDateBetween(
                searchFilterDto.getDateRange().getStartDate(),
                searchFilterDto.getDateRange().getEndDate()
            ))
            .and(hasRatingBetween(
                searchFilterDto.getRatingRange().getMinRating(),
                searchFilterDto.getRatingRange().getMaxRating()
            ))
    );

    List<CourseWithMinPriceAndRatingDto> results = courses.stream()
        .collect(Collectors.groupingBy(
            OfferableCourses::getCourse,
            Collectors.minBy(
                Comparator.comparingDouble(
                        (OfferableCourses o) -> o.getPrice() * (1 - o.getDiscount()))
                    .thenComparing(OfferableCourses::getDate)
            )
        ))
        .values().stream().filter(Optional::isPresent).map(Optional::get).map(o -> new CourseWithMinPriceAndRatingDto(
            o.getCourse(),
            o.getPrice() * (1 - o.getDiscount()),
            o.getDate(),
            1f
        )).collect(Collectors.toList());


    return ResponseEntity.status(200).body(results);
  }


}
