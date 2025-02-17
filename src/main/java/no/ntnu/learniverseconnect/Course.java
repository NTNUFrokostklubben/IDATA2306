package no.ntnu.learniverseconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
public class Course {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private int courseId;
  private String levelTitle;
  private int diffLevel;
  private Date closestCourse;
  private float credits;
  private int hoursWeek;
  private String relatedCert;
  private String description;
  @ManyToOne
  @JoinColumn(name="categoryId",foreignKey = @ForeignKey(name="FK_CATEGORY"))
  Category category;


  public String getLevelTitle() {
    return levelTitle;
  }

  public void setLevelTitle(String levelTitle) {
    this.levelTitle = levelTitle;
  }

  public int getDiffLevel() {
    return diffLevel;
  }

  public void setDiffLevel(int diffLevel) {
    this.diffLevel = diffLevel;
  }

  public Date getClosestCourse() {
    return closestCourse;
  }

  public void setClosestCourse(Date closestCourse) {
    this.closestCourse = closestCourse;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public int getHoursWeek() {
    return hoursWeek;
  }

  public void setHoursWeek(int hoursWeek) {
    this.hoursWeek = hoursWeek;
  }

  public String getRelatedCert() {
    return relatedCert;
  }

  public void setRelatedCert(String relatedCert) {
    this.relatedCert = relatedCert;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
