package com.alt23e9;

import com.alt23e9.dto.api.CloudflareCreateZoneDNSResponse;
import com.alt23e9.dto.api.CloudflareCreateZoneFirewallRuleResponse;
import com.alt23e9.dto.api.CloudflareFirewallRuleFilter;
import com.alt23e9.dto.api.CloudflareFirewallRulesResponse;
import com.alt23e9.dto.api.CloudflareListFilterResult;
import com.alt23e9.dto.api.CloudflareResponseDto;
import com.alt23e9.dto.api.CloudflareZoneDNSResult;
import com.alt23e9.dto.api.CloudflareZonesRequestDto;

import com.alt23e9.dto.api.CreateCloudflareDNSRecord;
import com.alt23e9.dto.api.CreateCloudflareFirewallRule;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
// @RegisterProvider(LoggingFilter.class)
public interface CloudflareApiClient {

  @GET
  @Path("zones")
  @Produces(MediaType.APPLICATION_JSON)
  CloudflareResponseDto findZone(
      @HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey,
      @QueryParam("account.id") String accountId,
      @QueryParam("name") String name);


  @POST
  @Path("zones")
  @Produces(MediaType.APPLICATION_JSON)
  CloudflareResponseDto createZone(
      @HeaderParam("Content-Type") String contentType,
      @HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey,
      CloudflareZonesRequestDto cloudflareZonesRequestDto);


  @GET
  @Path("zones/{zoneId}/dns_records")
  CloudflareZoneDNSResult getDNSRecords(@HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey, @PathParam("zoneId") String zoneId);

  @GET
  @Path("zones/{zoneId}/firewall/rules")
  CloudflareFirewallRulesResponse getFirewallRules(@HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey, @PathParam("zoneId") String zoneId);


  @DELETE
  @Path("zones/{zoneId}/dns_records/{dnsIdentifier}")
  void deleteDNSEntryForZone(@HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey
      , @PathParam("zoneId") String zoneId
      , @PathParam("dnsIdentifier") String dnsIdentifier);

  @POST
  @Path("zones/{zoneId}/dns_records")
  CloudflareCreateZoneDNSResponse createDNSRecord(@HeaderParam("X-Auth-Email") String email
      , @HeaderParam("X-Auth-Key") String globalKey
      , @PathParam("zoneId") String zoneId, CreateCloudflareDNSRecord record);

  @POST
  @Path("zones/{zoneId}/firewall/rules")
  CloudflareCreateZoneFirewallRuleResponse createFirewallRule(@HeaderParam("X-Auth-Email") String email
      , @HeaderParam("X-Auth-Key") String globalKey
      , @PathParam("zoneId") String zoneId, List<CreateCloudflareFirewallRule> firewallRules);

  @GET
  @Path("zones/{zoneId}/filters")
  @Produces(MediaType.APPLICATION_JSON)
  CloudflareListFilterResult getFilters(
      @HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey,
      @PathParam("zoneId") String zoneId,
      @QueryParam("description") String description);

  @POST
  @Path("zones/{zoneId}/filters")
  @Produces(MediaType.APPLICATION_JSON)
  CloudflareListFilterResult createFilter(
      @HeaderParam("X-Auth-Email") String email,
      @HeaderParam("X-Auth-Key") String globalKey,
      @PathParam("zoneId") String zoneId,
      List<CloudflareFirewallRuleFilter> data);

}
