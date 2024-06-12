package com.alt23e9.dto;

import com.alt23e9.dto.api.CloudflareZoneDNSEntry;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACloudflareDnsRecord {

  CloudflareZoneDNSEntry dnsRecord;

  ACloudflareZone zone;
}
