package no.ntnu.learniverseconnect.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class SearchFilterDto {
  private List<Integer> diffLevels;
  private CourseSizeRange courseSizeRange;
  private List<String> categories;
  private RatingRange ratingRange;
  private PriceRange priceRange;
  private DateRange dateRange;
  private String searchValue;

  // Getters and Setters
  public List<Integer> getDiffLevels() {
    return diffLevels;
  }

  public void setDiffLevels(List<Integer> diffLevels) {
    this.diffLevels = diffLevels;
  }

  public CourseSizeRange getCourseSizeRange() {
    return courseSizeRange;
  }

  public void setCourseSizeRange(CourseSizeRange courseSizeRange) {
    this.courseSizeRange = courseSizeRange;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public RatingRange getRatingRange() {
    return ratingRange;
  }

  public void setRatingRange(RatingRange ratingRange) {
    this.ratingRange = ratingRange;
  }

  public PriceRange getPriceRange() {
    return priceRange;
  }

  public void setPriceRange(PriceRange priceRange) {
    this.priceRange = priceRange;
  }

  public DateRange getDateRange() {
    return dateRange;
  }

  public void setDateRange(DateRange dateRange) {
    this.dateRange = dateRange;
  }

  public String getSearchValue() {
    return searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  // Inner classes for nested JSON objects
  public static class CourseSizeRange {
    @JsonProperty("min-credits")
    private float minCredits;
    @JsonProperty("max-credits")
    private float maxCredits;

    // Getters and Setters
    public float getMinCredits() {
      return minCredits;
    }

    public void setMinCredits(float minCredits) {
      this.minCredits = minCredits;
    }

    public float getMaxCredits() {
      return maxCredits;
    }

    public void setMaxCredits(float maxCredits) {
      this.maxCredits = maxCredits;
    }
  }

  public static class RatingRange {
    @JsonProperty("min-rating")
    private Double minRating;
    @JsonProperty("max-rating")
    private Double maxRating;

    // Getters and Setters
    public Double getMinRating() {
      return minRating;
    }

    public void setMinRating(Double minRating) {
      this.minRating = minRating;
    }

    public Double getMaxRating() {
      return maxRating;
    }

    public void setMaxRating(Double maxRating) {
      this.maxRating = maxRating;
    }
  }

  public static class PriceRange {
    @JsonProperty("min-price")
    private Float minPrice;
    @JsonProperty("max-price")
    private Float maxPrice;

    // Getters and Setters
    public Float getMinPrice() {
      return minPrice;
    }

    public void setMinPrice(Float minPrice) {
      this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
      return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
      this.maxPrice = maxPrice;
    }
  }

  public static class DateRange {
    private Date startDate;
    private Date endDate;

    // Getters and Setters
    public Date getStartDate() {
      return startDate;
    }

    public void setStartDate(Date startDate) {
      this.startDate = startDate;
    }

    public Date getEndDate() {
      return endDate;
    }

    public void setEndDate(Date endDate) {
      this.endDate = endDate;
    }
  }
}