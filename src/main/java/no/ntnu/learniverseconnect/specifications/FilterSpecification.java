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

public class FilterSpecification {

  public static Specification<OfferableCourses> hasTitle(String title) {
    if (title == null || title.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("course").get("title"),
        "%" + title + "%");
  }

  public static Specification<OfferableCourses> hasDescription(String description) {
    if (description == null || description.isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(
        root.get("course").get("description"), "%" + description + "%");
  }

  public static Specification<OfferableCourses> hasCategory(List<String> category) {
    return (root, query, criteriaBuilder) -> {
      if (category.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return root.get("course").get("category").in(category);
    };
  }

  public static Specification<OfferableCourses> hasDiffLevel(List<Integer> diffLevel) {
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
  public static Specification<OfferableCourses> hasCreditsBetween(float creditsMin,
                                                                  float creditsMax) {
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
    return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), priceMin,
        priceMax);
  }

  public static Specification<OfferableCourses> hasRatingBetween(Double minRating, Double maxRating) {
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

  public static Specification<OfferableCourses> hasDateBetween(Date dateFrom, Date dateTo) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("date"), dateFrom,
        dateTo);
  }

  public static Specification<OfferableCourses> hasVisibility(Boolean visibility) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visibility"),
        visibility);
  }


}
