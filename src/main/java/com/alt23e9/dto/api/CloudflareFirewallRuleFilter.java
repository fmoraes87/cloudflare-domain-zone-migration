package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudflareFirewallRuleFilter {

	@JsonProperty("paused")
	private boolean paused;

	@JsonProperty("expression")
	private String expression;

	@JsonProperty("description")
	private String description;

	@JsonProperty("ref")
	private String ref;

	@JsonProperty("id")
	private String id;
}