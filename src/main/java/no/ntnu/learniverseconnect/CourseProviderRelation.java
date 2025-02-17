package no.ntnu.learniverseconnect;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Represents the relation between course providers and the courses
 * they offer. Uses a composite key made from the course id and provider id
 *
 */
@Entity
public class CourseProviderRelation {
@EmbeddedId
private  CourseProviderRelationId id;
@ManyToOne()
@JoinColumns(
    @JoinColumn(name = "course", referencedColumnName ="courseId")
)

  @ManyToOne
  @MapsId("courseId")
  @JoinTable(
      name = "Course",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "provider_id")
  )
  private Course course;
  @ManyToOne
  @MapsId("providerId")
  @JoinColumn(name="providerId")
  private CourseProvider provider;
  @MapsId("date")
  private Date date;
  private float price;

}


/**
 * A class to create composite keys using jakarta.
 *
 * Made using Copilot
 */
@Embeddable
class CourseProviderRelationId implements Serializable {
  private int courseId;
  private int providerId;
  private Date date;

  // Default constructor
  public CourseProviderRelationId() {}

  // Parameterized constructor
  public CourseProviderRelationId(int courseId, int providerId, Date date) {
    this.courseId = courseId;
    this.providerId = providerId;
    this.date = date;
    ;
  }

  // Override equals and hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CourseProviderRelationId that = (CourseProviderRelationId) o;
    return this.courseId == that.courseId && this.providerId == that.providerId && that.date == this.date;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.courseId, this.providerId, this.date);
  }
}
