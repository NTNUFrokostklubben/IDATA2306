package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;

/**
 * Represents the relation between course providers and the courses
 * they offer. Uses a composite key made from the course id and provider id
 */
@Schema(description = "Course offering by a specific provider")
@Entity
public class OfferableCourses implements Serializable {
  @Schema(description = "Unique offering ID", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "Base course being offered")
  @ManyToOne
  private Course course;

  @Schema(description = "Provider offering the course")
  @ManyToOne
  private CourseProvider provider;

  @Schema(description = "Course start date", example = "2023-12-01")
  private Date date;

  @Schema(description = "Base price before discount", example = "99.99")
  private float price;

  @Schema(description = "Discount percentage (0-1)", example = "0.2")
  private float discount;

  @Schema(description = "Visibility status", example = "true")
  private boolean visibility;

  /**
   * Gets the unique ID of the offerable course.
   *
   * @return the offerable course ID
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the course associated with the offerable course.
   *
   * @return the course
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course associated with the offerable course.
   *
   * @param course the course to set
   */
  public void setCourse(Course course) {
    if (course == null) {
      throw new IllegalArgumentException("Course cannot be null");
    }
    this.course = course;
  }

  /**
   * Gets the provider associated with the offerable course.
   *
   * @return the provider
   */
  public CourseProvider getProvider() {

    return provider;
  }

  /**
   * Sets the provider associated with the offerable course.
   *
   * @param provider the provider to set
   */
  public void setProvider(CourseProvider provider) {
    if (provider == null) {
      throw new IllegalArgumentException("Course provider cannot be null");
    }
    this.provider = provider;
  }

  /**
   * Gets the date of the offerable course.
   *
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets the date of the offerable course.
   *
   * @param date the date to set
   */
  public void setDate(Date date) {
    if (date == null) {
      throw new IllegalArgumentException("date cannot be null");
    }
    this.date = date;
  }

  /**
   * Gets the price of the offerable course.
   *
   * @return the price
   */
  public float getPrice() {
    return price;
  }

  /**
   * Sets the price of the offerable course.
   *
   * @param price the price to set
   */
  public void setPrice(float price) {
    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }
    this.price = price;
  }

  /**
   * Gets the discount of the offerable course.
   *
   * @return the discount
   */
  public float getDiscount() {
    return discount;
  }

  /**
   * Sets the discount of the offerable course.
   *
   * @param discount the discount to set
   */
  public void setDiscount(float discount) {
    if (discount > 1.00 || discount < 0.00) {
      throw new IllegalArgumentException("discount must be between 1 and 0");
    }
    this.discount = discount;
  }

  /**
   * Gets the visibility status of the offerable course.
   *
   * @return the visibility status
   */
  public boolean isVisible() {
    return visibility;
  }

  /**
   * Sets the visibility status of the offerable course.
   *
   * @param visibility the visibility status to set
   */
  public void setVisibility(boolean visibility) {
    this.visibility = visibility;
  }
}


