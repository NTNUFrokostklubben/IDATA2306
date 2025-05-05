package no.ntnu.learniverseconnect.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Represents the keywords associated with a course.
 */

@Entity
public class Keywords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne
  private Course course;
  private String keyword;


  public Long getId() {
    return id;
  }

  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course associated with the keyword.
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
   * Gets the keyword associated with the course.
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * Sets the keyword associated with the course.
   *
   * @param keyword the keyword to set
   */

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
