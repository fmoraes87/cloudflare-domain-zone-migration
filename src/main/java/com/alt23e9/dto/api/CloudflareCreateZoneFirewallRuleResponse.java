package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudflareCreateZoneFirewallRuleResponse {

	@JsonProperty("result")
	private List<CloudflareFirewallRuleFilter> result;

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("messages")
	private List<String> messages;

	@JsonProperty("errors")
	private List<String> errors;
}