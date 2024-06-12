package com.alt23e9.dto;

import com.alt23e9.dto.api.CloudflareFirewallRule;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACloudflareFirewallRule {

  ACloudflareZone zone;

  CloudflareFirewallRule firewallRule;

}
