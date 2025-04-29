package no.ntnu.learniverseconnect.model.dto;

import java.util.Date;
import no.ntnu.learniverseconnect.model.entities.Course;

/**
 * Data Transfer Object for Course with minimum price. Used primarily for searching and filtering
 */
public class CourseWithMinPriceAndRatingDto {
  private Course course;
  private float minDiscountedPrice;
  private Date closestDate;
  private float rating;
  private int numberOfRatings;

  public CourseWithMinPriceAndRatingDto(Course course, float minDiscountedPrice, Date closestDate, float rating, int numberOfRatings) {
    this.course = course;
    this.minDiscountedPrice = minDiscountedPrice;
    this.closestDate = closestDate;
    this.rating = rating;
    this.numberOfRatings = numberOfRatings;
  }

  public Course getCourse() {
    return course;
  }

  public Float getMinDiscountedPrice() {
    return minDiscountedPrice;
  }

  public Date getClosestDate() {
    return closestDate;
  }

  public float getRating() {
    return rating;
  }
  public int getNumberOfRatings() {
    return numberOfRatings;
  }


}
