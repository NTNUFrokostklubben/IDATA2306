package no.ntnu.learniverseconnect.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;


/**
 * Represents a financial transaction record in the Learniverse Connect system.
 * Each transaction links a user to an offerable course they've purchased,
 * storing details about the purchase date and amount paid.
 *
 * <p>This entity is mapped to a database table and maintains relationships with:
 * <ul>
 *   <li>{@link User} - The purchaser of the course</li>
 *   <li>{@link OfferableCourses} - The course being purchased</li>
 * </ul>
 */
@Entity
public class Transaction {
  @Schema(description = "Unique transaction ID", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "User who made the purchase")
  @ManyToOne
  private User user;

  @Schema(description = "Purchased course offering details")
  @ManyToOne
  @JoinColumn(nullable = false)
  private OfferableCourses offerableCourses;

  @Schema(description = "Timestamp of transaction", example = "2023-01-01T12:00:00Z")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp timeOfTransaction;

  @Schema(description = "Final price paid (after discounts)", example = "49.99")
  private float pricePaid;
  /**
   * Sets the current timestamp before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.timeOfTransaction = new Timestamp(System.currentTimeMillis());
  }

  /**
   * Gets the user associated with this transaction.
   *
   * @return The user who made the transaction.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with this transaction.
   *
   * @param user The user to associate with this transaction.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the ID of the transaction.
   *
   * @return The unique identifier of the transaction.
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the offerable course associated with this transaction.
   *
   * @return The offerable course involved in the transaction.
   */
  public OfferableCourses getOfferableCourses() {
    return offerableCourses;
  }

  /**
   * Sets the offerable course associated with this transaction.
   *
   * @param offerableCourses The offerable course to associate with this transaction.
   */
  public void setOfferableCourses(OfferableCourses offerableCourses) {
    this.offerableCourses = offerableCourses;
  }

  /**
   * Gets the date of the transaction.
   *
   * @return The date when the transaction occurred.
   */
  public Timestamp getTimeOfTransaction() {
    return this.timeOfTransaction;
  }

  /**
   * Sets the date of the transaction.
   *
   * @param timestamp The date when the transaction occurred.
   */
  public void setTimeOfTransaction(Timestamp timestamp) {
    this.timeOfTransaction = timestamp;
  }

  /**
   * Gets the price paid for the transaction.
   *
   * @return The amount paid for the transaction.
   */
  public float getPricePaid() {
    return pricePaid;
  }

  /**
   * Sets the price paid for the transaction.
   *
   * @param pricePaid The amount paid for the transaction.
   */
  public void setPricePaid(float pricePaid) {
    this.pricePaid = pricePaid;
  }

}
