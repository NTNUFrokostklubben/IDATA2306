package no.ntnu.learniverseconnect;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
 */
@Entity
public class CourseProviderRelation {
  @Id
  private float priceId;
  @ManyToOne
  private Course course;
  @ManyToOne
  private CourseProvider provider;

  private Date date;
  private float price;
}


