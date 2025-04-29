package no.ntnu.learniverseconnect.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import org.springframework.data.jpa.domain.Specification;

/**
 * FilterSpecification class provides methods to create JPA specifications
 * (filtering based on criteria, primarily used by OfferableCoursesRepo and SearchController)
 */
public class FilterSpecification {

  /**
   * Filter courses by title containing a specific string
   *
   * @param title course title
   * @return specification for filtering courses by title
   */
  public static Specification<OfferableCourses> hasTitle(String title) {
    if (title == null || title.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("course").get("title"),
        "%" + title + "%");
  }

  /**
   * Filter courses by description containing a specific string
   *
   * @param description course description
   * @return specification for filtering courses by description
   */
  public static Specification<OfferableCourses> hasDescription(String description) {
    if (description == null || description.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(
        root.get("course").get("description"), "%" + description + "%");
  }

  /**
   * Filter courses by category
   *
   * @param category course category
   * @return specification for filtering courses by category
   */
  public static Specification<OfferableCourses> hasCategory(List<String> category) {
    if (category == null || category.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> {
      if (category.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return root.get("course").get("category").in(category);
    };
  }

  /**
   * Filter courses by difficulty level
   *
   * @param diffLevel course difficulty level
   * @return specification for filtering courses by difficulty level
   */
  public static Specification<OfferableCourses> hasDiffLevel(List<Integer> diffLevel) {
    if (diffLevel == null || diffLevel.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> {
      if (diffLevel.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return root.get("course").get("diffLevel").in(diffLevel);
    };
  }

  /**
   * Filter courses by credits
   *
   * @param creditsMin maximum credits
   * @param creditsMax minimum credits
   * @return specification for filtering courses by credits
   */
  public static Specification<OfferableCourses> hasCreditsBetween(Float creditsMin,
                                                                  Float creditsMax) {
    if (creditsMin == null && creditsMax == null) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> {
      Path<Float> creditsPath = root.get("course").get("credits");
      return criteriaBuilder.between(
          creditsPath, creditsMin, creditsMax);
    };
  }

  /**
   * Filter courses by price
   *
   * @param priceMin minimum price
   * @param priceMax maximum price
   * @return specification for filtering courses by price
   */
  public static Specification<OfferableCourses> hasPriceBetween(Float priceMin, Float priceMax) {
    if (priceMin == null && priceMax == null) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), priceMin,
        priceMax);
  }

  /**
   * Filter courses by rating
   * Joins UserCourse to Course to calculate average rating per course
   *
   * @param minRating minimum rating
   * @param maxRating maximum rating
   * @return specification for filtering courses by rating
   */
  public static Specification<OfferableCourses> hasRatingBetween(Double minRating,
                                                                 Double maxRating) {
    if (minRating == null && maxRating == null) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> {
      if (minRating == null && maxRating == null) {
        return criteriaBuilder.conjunction(); // No filtering if both are null
      }

      // Subquery to calculate average rating per course
      Subquery<Double> avgRatingSubquery = query.subquery(Double.class);
      Root<UserCourse> userCourseRoot = avgRatingSubquery.from(UserCourse.class);

      // Join UserCourse to Course (since Course is not directly mapped in UserCourse)
      Join<UserCourse, Course> userCourseCourseJoin = userCourseRoot.join("course");

      // Calculate average rating for the course
      avgRatingSubquery.select(criteriaBuilder.avg(userCourseRoot.get("rating")))
          .where(criteriaBuilder.equal(userCourseCourseJoin, root.get("course")));

      // Apply filtering based on the calculated average rating
      Predicate predicate = criteriaBuilder.conjunction();
      if (minRating != null) {
        predicate = criteriaBuilder.and(
            predicate,
            criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, minRating)
        );
      }
      if (maxRating != null) {
        predicate = criteriaBuilder.and(
            predicate,
            criteriaBuilder.lessThanOrEqualTo(avgRatingSubquery, maxRating)
        );
      }

      return predicate;
    };
  }

  /**
   * Filter courses by date
   *
   * @param dateFrom minimum date
   * @param dateTo   maximum date
   * @return
   */
  public static Specification<OfferableCourses> hasDateBetween(Date dateFrom, Date dateTo) {
    if (dateFrom == null && dateTo == null) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("date"), dateFrom,
        dateTo);
  }

  /**
   * Filter courses by visibility
   *
   * @param visibility true or false
   * @return specification for filtering courses by visibility
   */
  public static Specification<OfferableCourses> hasVisibility(Boolean visibility) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visibility"),
        visibility);
  }


}
