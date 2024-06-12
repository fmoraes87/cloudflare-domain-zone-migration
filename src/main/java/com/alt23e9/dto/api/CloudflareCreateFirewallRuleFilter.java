package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CloudflareCreateFirewallRuleFilter {

	@JsonProperty("paused")
	private boolean paused;

	@JsonProperty("expression")
	private String expression;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private String id;
}