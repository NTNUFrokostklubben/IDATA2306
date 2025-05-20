package no.ntnu.learniverseconnect.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The course provider statistics data transfer object.
 */
public class CourseProviderStatsDto {

  @JsonProperty("ID_PROVIDER")
  private final Long idProvider;

  @JsonProperty("PROVIDER_NAME")
  private final String providerName;

  @JsonProperty("REVENUE")
  private final float revenue;

  /**
   * Default constructor for CourseProviderStatsDto.
   *
   * @param idProvider the id of the course provider
   * @param providerName the name of the course provider
   * @param revenue the revenue of the course provider
   */
  public CourseProviderStatsDto(Long idProvider, String providerName, float revenue) {
    this.idProvider = idProvider;
    this.providerName = providerName;
    this.revenue = revenue;
  }


}
