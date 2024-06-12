package com.alt23e9;

import com.alt23e9.dto.ACloudflareAuth;
import com.alt23e9.dto.ACloudflareDnsRecord;
import com.alt23e9.dto.ACloudflareFirewallRule;
import com.alt23e9.dto.ACloudflareZone;
import java.util.List;
import java.util.Optional;

public interface CloudflareSyncer {

  ACloudflareZone copyZoneFrom(final ACloudflareAuth cloudflareFrom,final ACloudflareAuth cloudflareto, final String zoneName);

  Optional<ACloudflareZone> findZone(final ACloudflareAuth cloudflareFro, String zoneName);

  ACloudflareZone createZone(final ACloudflareAuth cloudflareTo, final String zoneName);

  Optional<List<ACloudflareFirewallRule>> getFirewallRules(final ACloudflareZone refZone);

  void copyDNS(final ACloudflareZone refZone, final ACloudflareZone zone);

  void copyWAF(final ACloudflareZone refZone, final ACloudflareZone zone);

  Optional<List<ACloudflareDnsRecord>> getDNSRecords(final ACloudflareZone zone);

  void cleanDnsEntriesFor(final ACloudflareZone zone);

  void deleteDNSEntry(ACloudflareDnsRecord dnsEntry);

}
