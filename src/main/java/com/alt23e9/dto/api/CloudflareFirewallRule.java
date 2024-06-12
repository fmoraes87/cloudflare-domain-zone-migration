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
public class CloudflareFirewallRule {

	@JsonProperty("filter")
	private CloudflareFirewallRuleFilter filter;

	@JsonProperty("paused")
	private boolean paused;

	@JsonProperty("ref")
	private String ref;

	@JsonProperty("description")
	private String description;

	@JsonProperty("action")
	private String action;

	@JsonProperty("id")
	private String id;
}