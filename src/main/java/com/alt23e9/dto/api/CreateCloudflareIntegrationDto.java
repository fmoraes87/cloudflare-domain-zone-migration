package com.alt23e9.dto.api;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCloudflareIntegrationDto implements Serializable {

  @NotNull
  private String name;
  @NotNull
  private String apiKey;
}
