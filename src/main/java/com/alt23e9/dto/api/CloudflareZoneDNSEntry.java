package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
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
public class CloudflareZoneDNSEntry implements Serializable {
	@JsonProperty("zone_name")
	private String zoneName;
	@JsonProperty("proxied")
	private boolean proxied;
	@JsonProperty("type")
	private String type;
	@JsonProperty("ttl")
	private int ttl;
	@JsonProperty("content")
	private String content;
	@JsonProperty("zone_id")
	private String zoneId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("comment")
	private String comment;
	@JsonProperty("id")
	private String id;
	@JsonProperty("locked")
	private boolean locked;
	@JsonProperty("proxiable")
	private boolean proxiable;
}