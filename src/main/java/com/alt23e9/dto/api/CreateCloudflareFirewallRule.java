package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCloudflareFirewallRule {

  @JsonProperty("filter")
  private CloudflareCreateFirewallRuleFilter filter;

  @JsonProperty("paused")
  private boolean paused;

  @JsonProperty("ref")
  private String ref;

  @JsonProperty("description")
  private String description;

  @JsonProperty("action")
  private String action;

}
