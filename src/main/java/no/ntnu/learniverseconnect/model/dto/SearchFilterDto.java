package no.ntnu.learniverseconnect.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for Search Filters.
 * This class is used to encapsulate the search filters for courses.
 */
public class SearchFilterDto {
  private List<Integer> diffLevels;
  private List<String> categories;
  private String searchValue;
  private CourseSizeRange courseSizeRange;
  private RatingRange ratingRange;
  private PriceRange priceRange;
  private DateRange dateRange;

  /**
   * Constructs a new SearchFilterDto with the specified parameters.
   *
   * @param diffLevels      the list of difficulty levels to filter by
   * @param categories      the list of categories to filter by
   * @param searchValue     the search term to filter by
   * @param courseSizeRange the range of course credits to filter by
   * @param ratingRange     the range of ratings to filter by
   * @param priceRange      the range of prices to filter by
   * @param dateRange       the date range to filter by
   */
  public SearchFilterDto(List<Integer> diffLevels, List<String> categories, String searchValue,
                         CourseSizeRange courseSizeRange, RatingRange ratingRange,
                         PriceRange priceRange,
                         DateRange dateRange) {
    this.diffLevels = diffLevels;
    this.categories = categories;
    this.searchValue = searchValue;
    this.courseSizeRange = courseSizeRange;
    this.ratingRange = ratingRange;
    this.priceRange = priceRange;
    this.dateRange = dateRange;
  }

  /**
   * Gets the list of difficulty levels.
   *
   * @return the list of difficulty levels
   */
  public List<Integer> getDiffLevels() {
    return diffLevels;
  }

  /**
   * Sets the list of difficulty levels.
   *
   * @param diffLevels the list of difficulty levels to set
   */
  public void setDiffLevels(List<Integer> diffLevels) {
    this.diffLevels = diffLevels;
  }

  /**
   * Gets the course size range.
   *
   * @return the course size range
   */
  public CourseSizeRange getCourseSizeRange() {
    return courseSizeRange;
  }

  /**
   * Sets the course size range.
   *
   * @param courseSizeRange the course size range to set
   */
  public void setCourseSizeRange(CourseSizeRange courseSizeRange) {
    this.courseSizeRange = courseSizeRange;
  }

  /**
   * Gets the list of categories.
   *
   * @return the list of categories
   */
  public List<String> getCategories() {
    return categories;
  }

  /**
   * Sets the list of categories.
   *
   * @param categories the list of categories to set
   */
  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  /**
   * Gets the rating range.
   *
   * @return the rating range
   */
  public RatingRange getRatingRange() {
    return ratingRange;
  }

  /**
   * Sets the rating range.
   *
   * @param ratingRange the rating range to set
   */
  public void setRatingRange(RatingRange ratingRange) {
    this.ratingRange = ratingRange;
  }

  /**
   * Gets the price range.
   *
   * @return the price range
   */
  public PriceRange getPriceRange() {
    return priceRange;
  }

  /**
   * Sets the price range.
   *
   * @param priceRange the price range to set
   */
  public void setPriceRange(PriceRange priceRange) {
    this.priceRange = priceRange;
  }

  /**
   * Gets the date range.
   *
   * @return the date range
   */
  public DateRange getDateRange() {
    return dateRange;
  }

  /**
   * Sets the date range.
   *
   * @param dateRange the date range to set
   */
  public void setDateRange(DateRange dateRange) {
    this.dateRange = dateRange;
  }

  /**
   * Gets the search value.
   *
   * @return the search value
   */
  public String getSearchValue() {
    return searchValue;
  }

  /**
   * Sets the search value.
   *
   * @param searchValue the search value to set
   */
  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  /**
   * Represents a range of course credits.
   */
  public static class CourseSizeRange {
    @JsonProperty("min-credits")
    private Float minCredits;
    @JsonProperty("max-credits")
    private Float maxCredits;

    /**
     * Constructs a new CourseSizeRange with the specified min and max credits.
     *
     * @param minCredits the minimum credits in the range
     * @param maxCredits the maximum credits in the range
     */
    public CourseSizeRange(Float minCredits, Float maxCredits) {
      this.minCredits = minCredits;
      this.maxCredits = maxCredits;
    }

    /**
     * Gets the minimum credits.
     *
     * @return the minimum credits
     */
    public Float getMinCredits() {
      return minCredits;
    }

    /**
     * Sets the minimum credits.
     *
     * @param minCredits the minimum credits to set
     */
    public void setMinCredits(Float minCredits) {
      this.minCredits = minCredits;
    }

    /**
     * Gets the maximum credits.
     *
     * @return the maximum credits
     */
    public Float getMaxCredits() {
      return maxCredits;
    }

    /**
     * Sets the maximum credits.
     *
     * @param maxCredits the maximum credits to set
     */
    public void setMaxCredits(Float maxCredits) {
      this.maxCredits = maxCredits;
    }
  }

  /**
   * Represents a range of ratings.
   */
  public static class RatingRange {
    @JsonProperty("min-rating")
    private Double minRating;
    @JsonProperty("max-rating")
    private Double maxRating;

    /**
     * Constructs a new RatingRange with the specified min and max ratings.
     *
     * @param minRating the minimum rating in the range
     * @param maxRating the maximum rating in the range
     */
    public RatingRange(Double minRating, Double maxRating) {
      this.minRating = minRating;
      this.maxRating = maxRating;
    }

    /**
     * Gets the minimum rating.
     *
     * @return the minimum rating
     */
    public Double getMinRating() {
      return minRating;
    }

    /**
     * Sets the minimum rating.
     *
     * @param minRating the minimum rating to set
     */
    public void setMinRating(Double minRating) {
      this.minRating = minRating;
    }

    /**
     * Gets the maximum rating.
     *
     * @return the maximum rating
     */
    public Double getMaxRating() {
      return maxRating;
    }

    /**
     * Sets the maximum rating.
     *
     * @param maxRating the maximum rating to set
     */
    public void setMaxRating(Double maxRating) {
      this.maxRating = maxRating;
    }
  }

  /**
   * Represents a range of prices.
   */
  public static class PriceRange {
    @JsonProperty("min-price")
    private Float minPrice;
    @JsonProperty("max-price")
    private Float maxPrice;

    /**
     * Constructs a new PriceRange with the specified min and max prices.
     *
     * @param minPrice the minimum price in the range
     * @param maxPrice the maximum price in the range
     */
    public PriceRange(Float minPrice, Float maxPrice) {
      this.minPrice = minPrice;
      this.maxPrice = maxPrice;
    }

    /**
     * Gets the minimum price.
     *
     * @return the minimum price
     */
    public Float getMinPrice() {
      return minPrice;
    }

    /**
     * Sets the minimum price.
     *
     * @param minPrice the minimum price to set
     */
    public void setMinPrice(Float minPrice) {
      this.minPrice = minPrice;
    }

    /**
     * Gets the maximum price.
     *
     * @return the maximum price
     */
    public Float getMaxPrice() {
      return maxPrice;
    }

    /**
     * Sets the maximum price.
     *
     * @param maxPrice the maximum price to set
     */
    public void setMaxPrice(Float maxPrice) {
      this.maxPrice = maxPrice;
    }
  }

  /**
   * Represents a range of dates.
   */
  public static class DateRange {
    private Date startDate;
    private Date endDate;

    /**
     * Constructs a new DateRange with the specified start and end dates.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     */
    public DateRange(Date startDate, Date endDate) {
      this.startDate = startDate;
      this.endDate = endDate;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
      return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
      this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
      return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
      this.endDate = endDate;
    }
  }
}