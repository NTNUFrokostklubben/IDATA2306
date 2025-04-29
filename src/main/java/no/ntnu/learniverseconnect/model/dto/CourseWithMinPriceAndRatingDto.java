package no.ntnu.learniverseconnect.model.dto;

import java.util.Date;
import no.ntnu.learniverseconnect.model.entities.Course;

/**
 * Data Transfer Object for Course with minimum price. Used primarily for searching and filtering
 */
public class CourseWithMinPriceAndRatingDto {
  private Course course;
  private float minDiscounredtPrice;
  private Date closestDate;
  private float rating;

  public CourseWithMinPriceAndRatingDto(Course course, float minDiscounredtPrice, Date closestDate, float rating) {
    this.course = course;
    this.minDiscounredtPrice = minDiscounredtPrice;
    this.closestDate = closestDate;
    this.rating = rating;
  }

  public Course getCourse() {
    return course;
  }

  public Float getMinDiscounredtPrice() {
    return minDiscounredtPrice;
  }

  public Date getClosestDate() {
    return closestDate;
  }

  public float getRating() {
    return rating;
  }


}
