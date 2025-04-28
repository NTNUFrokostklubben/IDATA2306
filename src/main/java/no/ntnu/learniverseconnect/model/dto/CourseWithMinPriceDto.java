package no.ntnu.learniverseconnect.model.dto;

import java.util.Date;
import no.ntnu.learniverseconnect.model.entities.Course;

/**
 * Data Transfer Object for Course with minimum price. Used primarily for searching and filtering
 */
public class CourseWithMinPriceDto {
  private Course course;
  private float minDiscounredtPrice;
  private Date closestDate;

  public CourseWithMinPriceDto(Course course, float minDiscounredtPrice, Date closestDate) {
    this.course = course;
    this.minDiscounredtPrice = minDiscounredtPrice;
    this.closestDate = closestDate;
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


}
