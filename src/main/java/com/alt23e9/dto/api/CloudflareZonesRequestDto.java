package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudflareZonesRequestDto implements Serializable {

  @JsonProperty("account")
  private CloudflareZonesAccountRequestDto account;

  @JsonProperty("name")
  private String name;

  @JsonProperty("type")
  private String type;

}
