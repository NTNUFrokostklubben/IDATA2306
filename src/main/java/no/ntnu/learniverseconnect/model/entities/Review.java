package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Date;

@Schema(description = "Represents a user's review/rating for a course")
@Entity
public class Review {
  @Schema(description = "Unique identifier of the review", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "Rating value (1-5 scale)", example = "4", minimum = "1", maximum = "5")
  private int rating;

  @Schema(description = "Date when the review was submitted", example = "2023-01-01")
  private Date reviewDate;

  @Schema(description = "Detailed review comment (max 2048 chars)",
      example = "Great course with comprehensive materials")
  @Column(length = 2048)
  private String comment;

  @Schema(description = "Title/summary of the review", example = "Highly Recommended")
  private String title;

  public Review() {
    this.reviewDate = new Date(System.currentTimeMillis());
  }

  /**
   * Gets the unique identifier of the rating.
   *
   * @return The rating ID.
   */
  public Long getId() {
    return id;
  }


  public void setDate() {
    this.reviewDate = new Date(System.currentTimeMillis());
  }

  /**
   * Gets the numerical rating value.
   *
   * @return The rating value (typically on a scale, e.g., 1-5).
   */
  public int getRating() {
    return rating;
  }

  /**
   * Sets the numerical rating value.
   *
   * @param rating The rating value to set.
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * Gets the date when the rating was created.
   *
   * @return The rating creation date.
   */
  public Date getDate() {
    return reviewDate;
  }

  /**
   * Gets the comment associated with the rating.
   *
   * @return The rating comment (up to 2048 characters).
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets the comment associated with the rating.
   *
   * @param comment The comment to set (max 2048 characters).
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Gets the title of the rating.
   *
   * @return The rating title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the rating.
   *
   * @param title The title to set.
   */
  public void setTitle(String title) {
    this.title = title;
  }

}
