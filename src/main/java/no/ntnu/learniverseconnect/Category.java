package no.ntnu.learniverseconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a category for a course.
 */
@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String categoryName;
}
