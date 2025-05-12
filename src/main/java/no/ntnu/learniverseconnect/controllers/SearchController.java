package no.ntnu.learniverseconnect.controllers;

import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasCategory;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasCreditsBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDateBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDescription;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasDiffLevel;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasKeywords;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasPriceBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasRatingBetween;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasTitle;
import static no.ntnu.learniverseconnect.specifications.FilterSpecification.hasVisibility;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceAndRatingDto;
import no.ntnu.learniverseconnect.model.dto.SearchFilterDto;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.OfferableCoursesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling search requests.
 */
@RestController
public class SearchController {

  private final OfferableCoursesRepo offerableCoursesRepo;
  private final UserCoursesRepo userCoursesRepo;
  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);


  /**
   * Constructor for SearchController.
   *
   * @param offerableCoursesController Offerable courses repository
   * @param userCoursesRepo            UserCourses repository, for Ratings
   */
  public SearchController(
      OfferableCoursesRepo offerableCoursesController,
      UserCoursesRepo userCoursesRepo) {

    this.offerableCoursesRepo = offerableCoursesController;
    this.userCoursesRepo = userCoursesRepo;
  }


  /**
   * Searches for courses based on the provided search parameters from URL.
   *
   * @param diffLevels List of difficulty levels
   * @param categories List of categories
   * @param search     Search string
   * @param minCredits (min-credits) Minimum credits
   * @param maxCredits (max-credits) Maximum credits
   * @param minRating  (min-rating) Minimum rating
   * @param maxRating  (max-rating) Maximum rating
   * @param minPrice   (min-price) Minimum price
   * @param maxPrice   (max-price) Maximum price
   * @param startDate  Start date in milliseconds since epoch
   * @param endDate    End date in milliseconds since epoch
   * @return
   */
  @GetMapping("/search")
  public ResponseEntity<List<CourseWithMinPriceAndRatingDto>> searchCourse(
      @RequestParam(required = false) List<Integer> diffLevels,
      @RequestParam(required = false) List<String> categories,
      @RequestParam(required = false) String search,
      @RequestParam(required = false, name = "min-credits") Float minCredits,
      @RequestParam(required = false, name = "max-credits") Float maxCredits,
      @RequestParam(required = false, name = "min-rating") Double minRating,
      @RequestParam(required = false, name = "max-rating") Double maxRating,
      @RequestParam(required = false, name = "min-price") Float minPrice,
      @RequestParam(required = false, name = "max-price") Float maxPrice,
      @RequestParam(required = false) Long startDate,
      @RequestParam(required = false) Long endDate
  ) {

    SearchFilterDto searchFilterDto = new SearchFilterDto(
        diffLevels,
        categories,
        search,
        new SearchFilterDto.CourseSizeRange(minCredits, maxCredits),
        new SearchFilterDto.RatingRange(minRating, maxRating),
        new SearchFilterDto.PriceRange(minPrice, maxPrice),
        new SearchFilterDto.DateRange(
            Optional.ofNullable(startDate).map(Date::new).orElse(null),
            Optional.ofNullable(endDate).map(Date::new).orElse(null)
        )
    );

    // Log the incoming filter for debugging
    logger.info("Received search filter: {}", searchFilterDto);


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
                    hasDescription(searchFilterDto.getSearchValue()))
                .or(hasKeywords(searchFilterDto.getSearchValue())))
            .and(hasDateBetween(
                searchFilterDto.getDateRange().getStartDate(),
                searchFilterDto.getDateRange().getEndDate()
            ))
            .and(hasRatingBetween(
                searchFilterDto.getRatingRange().getMinRating(),
                searchFilterDto.getRatingRange().getMaxRating()
            ))
    );

    List<CourseWithMinPriceAndRatingDto> filteredResult = new ArrayList<>(courses.stream()
        .collect(Collectors.groupingBy(
            OfferableCourses::getCourse,
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                  // Find offer with min discounted price and closest date
                  OfferableCourses bestOffer = list.stream()
                      .min(Comparator.comparingDouble(
                              (OfferableCourses o) -> o.getPrice() * (1 - o.getDiscount()))
                          .thenComparing(OfferableCourses::getDate))
                      .orElseThrow();

                  // Calculate average rating (assuming UserCourseRepo is available)
                  Float avgRating =
                      userCoursesRepo.getAverageRatingByCourseId(bestOffer.getCourse().getId());
                  int numberOfRatings =
                      userCoursesRepo.countByCourseId(bestOffer.getCourse().getId());
                  return new CourseWithMinPriceAndRatingDto(
                      bestOffer.getCourse(),
                      bestOffer.getPrice() * (1 - bestOffer.getDiscount()),
                      bestOffer.getDate(),
                      avgRating != null ? avgRating.floatValue() : 0f,
                      numberOfRatings
                  );
                }
            )
        )).values());

    return ResponseEntity.status(200).body(filteredResult);
  }


  /**
   * Searches for courses based on the provided search filter JSON object.
   *
   * @param searchFilterDto The search filter object containing various search parameters.
   * @return A ResponseEntity containing a list of CourseWithMinPriceAndRatingDto objects.
   * @deprecated Can be used for admin filtering, but is deprecated in favor of the URL-based search for customers.
   */
  @Deprecated(
      since = "1.0",
      forRemoval = false
  )
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

    List<CourseWithMinPriceAndRatingDto> filteredResult = new ArrayList<>(courses.stream()
        .collect(Collectors.groupingBy(
            OfferableCourses::getCourse,
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                  // Find offer with min discounted price and closest date
                  OfferableCourses bestOffer = list.stream()
                      .min(Comparator.comparingDouble(
                              (OfferableCourses o) -> o.getPrice() * (1 - o.getDiscount()))
                          .thenComparing(OfferableCourses::getDate))
                      .orElseThrow();

                  // Calculate average rating (assuming UserCourseRepo is available)
                  Float avgRating =
                      userCoursesRepo.getAverageRatingByCourseId(bestOffer.getCourse().getId());
                  int numberOfRatings =
                      userCoursesRepo.countByCourseId(bestOffer.getCourse().getId());
                  return new CourseWithMinPriceAndRatingDto(
                      bestOffer.getCourse(),
                      bestOffer.getPrice() * (1 - bestOffer.getDiscount()),
                      bestOffer.getDate(),
                      avgRating != null ? avgRating.floatValue() : 0f,
                      numberOfRatings
                  );
                }
            )
        )).values());

    return ResponseEntity.status(200).body(filteredResult);
  }


}
