package com.alt23e9.dto;

import com.alt23e9.dto.api.CloudflareFirewallRule;
import com.alt23e9.dto.api.CloudflareFirewallRuleFilter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACloudflareFirewallRuleFilter {

  ACloudflareZone zone;

  CloudflareFirewallRuleFilter firewallRuleFilter;

}
