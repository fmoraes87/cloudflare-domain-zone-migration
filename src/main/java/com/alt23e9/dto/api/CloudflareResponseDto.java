package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudflareResponseDto implements Serializable {

  private Object result;
  private HashMap<String, BigDecimal> result_info;
  private Boolean success;
  private Object errors;
  private Object messages;
}
