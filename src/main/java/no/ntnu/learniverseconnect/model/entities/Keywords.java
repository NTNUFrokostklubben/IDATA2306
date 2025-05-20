package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Represents the keywords associated with a course.
 */
@Tag(name = "Keywords", description = "Keywords associated with a course")
@Entity
public class Keywords {
  @Schema(description = "Unique identifier of the keyword", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
    @Schema(description = "Course associated with the keyword")
  @ManyToOne(cascade= CascadeType.REMOVE)
  private Course course;
    @Schema(description = "Keyword associated with the course", example = "Java")
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
