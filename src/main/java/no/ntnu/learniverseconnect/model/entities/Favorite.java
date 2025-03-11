package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Favorite {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Course course;
  @ManyToOne
  private User user;

  public Favorite(User user, Course course){
    this.user = user;
    this.course = course;
  }

  /**
   * Gets the unique ID of the favorite.
   *
   * @return the favorite ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the course associated with the favorite.
   *
   * @return the course
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course associated with the favorite.
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
   * Gets the user who made the favorite.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who made the favorite.
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
