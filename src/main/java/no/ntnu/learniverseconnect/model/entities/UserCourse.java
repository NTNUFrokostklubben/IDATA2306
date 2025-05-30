package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Represents the ratings made by a user, associated with a course.
 */
@Schema(description = "Represents a user's enrollment in a course and associated review")
@Entity
public class UserCourse {

  @Schema(description = "Unique ID of the user-course relationship", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Review associated with the enrollment")
  @OneToOne
  private Review review;

  @Schema(description = "Timestamp of enrollment/review", example = "2023-01-01T12:00:00Z")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp timestamp;
  @Schema(description = "Course associated with the enrollment")
  @ManyToOne()
  @JoinColumn(nullable = false)
  private Course course;
  @Schema(description = "User associated with the enrollment")
  @ManyToOne()
  @JoinColumn(nullable = false)
  private User user;

  /**
   * Sets the current timestamp before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  /**
   * Gets the unique ID of the rating.
   *
   * @return the rating ID
   */
  public Long getId() {
    return id;
  }

  public void setTimestamp(){
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
  /**
   * Gets the rating value.
   *
   * @return the rating value
   */
  public Review getReview() {
    return review;
  }

  /**
   * Sets the rating value.
   *
   * @param rating the rating value to set
   */
  public void setReview(Review rating) {
    this.review = rating;
  }


    /**
     * Gets the date when the rating was made.
     *
     * @return the date
     */
    public Timestamp getTimestamp() {
      return this.timestamp;

    }

  /**
   * Gets the course associated with the rating.
   *
   * @return the course
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course associated with the rating.
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
   * Gets the user who made the rating.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who made the rating.
   *
   * @param user the user to set
   */
  public void setUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    this.user = user;
  }



}
