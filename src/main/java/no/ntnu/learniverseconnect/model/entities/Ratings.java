package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Represents the ratings made by a user, associated with a course
 */

@Entity
public class Ratings {
  @Id
  @GeneratedValue
  private Long id;
  private int rating;
  private String comment;
  @ManyToOne
  private Course course;
  @ManyToOne
  private User user;

  /**
   * Gets the unique ID of the rating.
   *
   * @return the rating ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the rating value.
   *
   * @return the rating value
   */
  public int getRating() {
    return rating;
  }

  /**
   * Sets the rating value.
   *
   * @param rating the rating value to set
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * Gets the comment for the rating.
   *
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets the comment for the rating.
   *
   * @param comment the comment to set
   */
  public void setComment(String comment) {
    this.comment = comment;
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
      throw new IllegalArgumentException("Course cannot be null");
    }
    this.user = user;
  }


}
