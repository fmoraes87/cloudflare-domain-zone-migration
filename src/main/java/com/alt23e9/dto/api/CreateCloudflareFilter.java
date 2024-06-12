package com.alt23e9.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCloudflareFilter {

 private List<CloudflareFirewallRuleFilter> data;

}
