package no.ntnu.learniverseconnect.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseProviderStatsDto {

  @JsonProperty("ID_PROVIDER")
  private final Long idProvider;

  @JsonProperty("PROVIDER_NAME")
  private final String providerName;

  @JsonProperty("REVENUE")
  private final float revenue;

  public CourseProviderStatsDto(Long idProvider, String providerName, float revenue){
    this.idProvider = idProvider;
    this.providerName = providerName;
    this.revenue = revenue;
  }



}
