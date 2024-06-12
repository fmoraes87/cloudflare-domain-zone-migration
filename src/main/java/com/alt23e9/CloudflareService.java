package com.alt23e9;

import com.alt23e9.dto.ACloudflareAuth;
import com.alt23e9.dto.ACloudflareDnsRecord;
import com.alt23e9.dto.ACloudflareFirewallRule;
import com.alt23e9.dto.ACloudflareFirewallRuleFilter;
import com.alt23e9.dto.api.CloudflareCreateFirewallRuleFilter;
import com.alt23e9.dto.api.CloudflareListFilterResult;
import com.alt23e9.dto.api.CloudflareFirewallRuleFilter;
import com.alt23e9.dto.api.CloudflareFirewallRulesResponse;
import com.alt23e9.dto.api.CloudflareResponseDto;
import com.alt23e9.dto.api.CloudflareZoneDNSResult;
import com.alt23e9.dto.api.CloudflareZonesAccountRequestDto;
import com.alt23e9.dto.api.CloudflareZonesRequestDto;
import com.alt23e9.dto.api.CloudflareZonesResponseDto;
import com.alt23e9.dto.ACloudflareZone;
import com.alt23e9.dto.api.CreateCloudflareDNSRecord;
import com.alt23e9.dto.api.CreateCloudflareFilter;
import com.alt23e9.dto.api.CreateCloudflareFirewallRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
@Log
public class CloudflareService implements CloudflareSyncer{

  @Override
  public ACloudflareZone copyZoneFrom(final ACloudflareAuth cloudflareFrom,final ACloudflareAuth cloudflareTo, final String zoneName) {
      Optional<ACloudflareZone> zoneResp = findZone(cloudflareFrom,zoneName);
      if (zoneResp.isEmpty()){
        throw new CloudflareException(zoneName + " doesnt exists");
      }

      ACloudflareZone refZone = zoneResp.get();
      Optional<ACloudflareZone> newZoneResp = findZone(cloudflareTo,refZone.getName());
      ACloudflareZone newZone;
      if (newZoneResp.isEmpty()){
        newZone = createZone(cloudflareTo,zoneName);
      }else{
        newZone = newZoneResp.get();
      }

      copyDNS(refZone,newZone);
      copyWAF(refZone,newZone);

      log.info("Finish: " + zoneName);

      return newZone;
  }

  public void copyWAF(final ACloudflareZone refZone, final ACloudflareZone newZone) {
    Optional<List<ACloudflareFirewallRule>> firewallRulesResp = getFirewallRules(refZone);
    if (firewallRulesResp.isEmpty()){
      return;
    }

    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var firewallRules = firewallRulesResp.get();

    var cloudflareAuth = newZone.getAuth();


    firewallRules.forEach(firewallRule -> {
      try{
        var filterRef = firewallRule.getFirewallRule().getFilter();
        ACloudflareFirewallRuleFilter firewallRuleFilter = null;
        if (null!=filterRef){
          var resp = findFilterByDescription(newZone,filterRef.getDescription());
          if (resp.isEmpty()){
            firewallRuleFilter = createFirewallRuleFilter(newZone,filterRef);
          }else{
            firewallRuleFilter = resp.get();
          }
        }

        if (firewallRuleFilter==null){
          log.severe("Error creating firewall rule:" + firewallRule.getFirewallRule().getDescription());

          return;
        }

       cloudflareApiClient.createFirewallRule(
            cloudflareAuth.getEmail(),
            cloudflareAuth.getApiKey(),
            newZone.getId(),
            List.of(
            CreateCloudflareFirewallRule.builder()
                .ref(firewallRule.getFirewallRule().getRef())
                .paused(firewallRule.getFirewallRule().isPaused())
                .action(firewallRule.getFirewallRule().getAction())
                .description(firewallRule.getFirewallRule().getDescription())
                .filter(CloudflareCreateFirewallRuleFilter.builder()
                    .id(firewallRuleFilter.getFirewallRuleFilter().getId())
                    .build()
                )
                .build()));
      }catch (final WebApplicationException e) {
        log.severe(e.getMessage());
        log.severe("Error creating firewall rule:" + firewallRule.getFirewallRule().getDescription());

      }

    });
  }

  public ACloudflareFirewallRuleFilter createFirewallRuleFilter( final ACloudflareZone zone,final CloudflareFirewallRuleFilter filterRef) {
    try{

      CloudflareApiClient cloudflareApiClient =
          getCloudflareApiClient();


      var cloudflareAuth= zone.getAuth();

      var response = cloudflareApiClient.createFilter(
          cloudflareAuth.getEmail(),
          cloudflareAuth.getApiKey(),
          zone.getId(),
          CreateCloudflareFilter.builder().data(
              List.of(
              CloudflareFirewallRuleFilter.builder()
                  .description(filterRef.getDescription())
                  .expression(filterRef.getExpression())
                  .paused(filterRef.isPaused())
                  .ref(filterRef.getRef())
                  .build()))
              .build().getData());

      if (response.isSuccess()){
        return ACloudflareFirewallRuleFilter.builder()
            .zone(zone)
            .firewallRuleFilter(response.getResult().get(0))
            .build();
      }else{
        throw new CloudflareException("Nao foi possivel criar filtro: " + filterRef.getDescription());
      }
    }catch (final WebApplicationException e) {
      log.severe(e.getMessage());
      log.severe("Filter:" + filterRef.getDescription());
      return null;
    }
  }

  public Optional<ACloudflareFirewallRuleFilter> findFilterByDescription(ACloudflareZone zone , final String description) {
    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var cloudflareAuth = zone.getAuth();

    CloudflareListFilterResult response =
        cloudflareApiClient.getFilters( cloudflareAuth.getEmail(), cloudflareAuth.getApiKey(),zone.getId(), description);

    if (!response.isSuccess()){
      throw new CloudflareException("Não foi possível verificar DNS do dominio " + zone.getName());
    }

    if (null==response.getResult() || response.getResult().size() == 0){
      return Optional.empty();
    }

    //should exists only 1
    var filter = response.getResult().get(0);

    return Optional.of(
            ACloudflareFirewallRuleFilter.builder()
                .firewallRuleFilter(filter)
                .zone(zone)
                .build());

  }

  public Optional<List<ACloudflareFirewallRule>> getFirewallRules(final ACloudflareZone zone) {
    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var cloudflareAuth = zone.getAuth();

    CloudflareFirewallRulesResponse response =
        cloudflareApiClient.getFirewallRules( cloudflareAuth.getEmail(), cloudflareAuth.getApiKey(),zone.getId());

    if (!response.isSuccess()){
      throw new CloudflareException("Não foi possível obter as regras de firewaal dominio " + zone.getName());
    }

    return Optional.of(response.getResult().stream().map( firewallRule ->
            ACloudflareFirewallRule.builder()
                .firewallRule(firewallRule)
                .zone(zone)
                .build())
        .collect(Collectors.toList()));
  }

  public void copyDNS(final ACloudflareZone refZone, final ACloudflareZone newZone) {
    cleanDnsEntriesFor(newZone);
    Optional<List<ACloudflareDnsRecord>> dnsRecordsResp = getDNSRecords(refZone);
    if (dnsRecordsResp.isEmpty()){
      return;
    }

    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var dnsRecords = dnsRecordsResp.get();

    var cloudflareAuth = newZone.getAuth();


    dnsRecords.forEach(aDnsEntry -> {
      try{
        var dnsEntry = aDnsEntry.getDnsRecord();
        var response = cloudflareApiClient.createDNSRecord(
            cloudflareAuth.getEmail(),
            cloudflareAuth.getApiKey(),
            newZone.getId(),
            CreateCloudflareDNSRecord.builder()
                .content(dnsEntry.getContent())
                .name(dnsEntry.getName())
                .type(dnsEntry.getType())
                .proxied(dnsEntry.isProxied())
                .comment(dnsEntry.getComment())
                .ttl(dnsEntry.getTtl())
                .build());
      }catch (final WebApplicationException e) {
        log.severe(e.getMessage());
        log.severe("Domain:" + newZone.getName());
        log.severe("Entry Name:" + aDnsEntry.getDnsRecord().getName());
        log.severe("Entry Type:" + aDnsEntry.getDnsRecord().getContent());


      }

    });


  }

  public void cleanDnsEntriesFor(final ACloudflareZone zone) {
      Optional<List<ACloudflareDnsRecord>> dnsRecords = getDNSRecords(zone);
      if (dnsRecords.isEmpty()){
        return;
      }

    dnsRecords.get().forEach(this::deleteDNSEntry);
  }

  public void deleteDNSEntry(ACloudflareDnsRecord aDNSEntry) {
    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var zone = aDNSEntry.getZone();
    var cloudflareAuth = zone.getAuth();
    var dnsEntry = aDNSEntry.getDnsRecord();

    cloudflareApiClient.deleteDNSEntryForZone( cloudflareAuth.getEmail()
        , cloudflareAuth.getApiKey()
        , zone.getId()
        , dnsEntry.getId());
  }

  public Optional<List<ACloudflareDnsRecord>> getDNSRecords(final ACloudflareZone zone) {
    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    var cloudflareAuth = zone.getAuth();

    CloudflareZoneDNSResult response =
        cloudflareApiClient.getDNSRecords( cloudflareAuth.getEmail(), cloudflareAuth.getApiKey(),zone.getId());

    if (!response.isSuccess()){
      throw new CloudflareException("Não foi possível verificar DNS do dominio " + zone.getName());
    }

    return Optional.of(response.getResult().stream().map( dnsEntry ->
        ACloudflareDnsRecord.builder()
            .dnsRecord(dnsEntry)
            .zone(zone)
            .build())
        .collect(Collectors.toList()));


  }

  @Override
  public ACloudflareZone createZone(final ACloudflareAuth cloudflareTo, final String zoneName) {
    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    final CloudflareZonesRequestDto cloudflareZonesRequestDto =
        CloudflareZonesRequestDto.builder()
            .name(zoneName)
            .account(
                CloudflareZonesAccountRequestDto.builder()
                    .id(cloudflareTo.getAccountId())
                    .build())
            .type("full")
            .build();

    var cloudflareResponseDto =
        cloudflareApiClient.createZone(
            "application/json", cloudflareTo.getEmail(), cloudflareTo.getApiKey(), cloudflareZonesRequestDto);


    final CloudflareZonesResponseDto cloudflareZonesResponseDto =
        new ObjectMapper().convertValue(
            cloudflareResponseDto.getResult(), CloudflareZonesResponseDto.class);

    return ACloudflareZone.builder().id(cloudflareZonesResponseDto.getId())
        .name(cloudflareZonesResponseDto.getName())
        .auth(cloudflareTo).build();
  }

  @Override
  public Optional<ACloudflareZone> findZone(final ACloudflareAuth cloudflareFrom, final String zoneName) {

    CloudflareApiClient cloudflareApiClient =
        getCloudflareApiClient();

    final CloudflareResponseDto cloudflareResponseDto =
        cloudflareApiClient.findZone(cloudflareFrom.getEmail(), cloudflareFrom.getApiKey(),cloudflareFrom.getAccountId(), zoneName);

    if (!cloudflareResponseDto.getSuccess()) {
      throw new CloudflareException("Erro ao se comunicar com a API do Cloudflare!");
    }

    final ObjectMapper objectMapper = new ObjectMapper();
    final List<CloudflareZonesResponseDto> cloudflareAccountResponseDtoList =
        objectMapper.convertValue(cloudflareResponseDto.getResult(), new TypeReference<>() {});

    if (cloudflareAccountResponseDtoList.size() > 0){
      CloudflareZonesResponseDto zone = cloudflareAccountResponseDtoList.get(0);
      return Optional.of(ACloudflareZone.builder().auth(cloudflareFrom)
          .id(zone.getId())
          .name(zone.getName()).build());
    }

    return Optional.empty();
  }

  private static CloudflareApiClient getCloudflareApiClient() {

    return RestClientBuilder.newBuilder()
        .baseUri(URI.create("https://api.cloudflare.com/client/v4/"))
        .build(CloudflareApiClient.class);
  }

}
