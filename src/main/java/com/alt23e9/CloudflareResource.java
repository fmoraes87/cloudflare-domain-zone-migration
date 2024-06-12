package com.alt23e9;

import com.alt23e9.dto.ACloudflareAuth;
import com.alt23e9.dto.ACloudflareZone;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class CloudflareResource {

  @ConfigProperty(name = "FROM_CLOUDFLARE_GLOBAL_KEY")
  String cloudflareGlobalKeyFrom;

  @ConfigProperty(name = "FROM_CLOUDFLARE_EMAIL")
  String emailFrom;

  @ConfigProperty(name = "TO_CLOUDFLARE_GLOBAL_KEY")
  String cloudflareGlobalKeyTo;

  @ConfigProperty(name = "TO_CLOUDFLARE_EMAIL")
  String emailto;

  @Inject CloudflareSyncer cloudflareService;

  @Query("hello")
  public String hello() {
    return "hello";
  }

  @Mutation("copyZone")
  public ACloudflareZone copyZone(@NonNull String accountIdFrom, @NonNull String accountIdTo,@NonNull  String domain){
    return this.cloudflareService.copyZoneFrom(
        ACloudflareAuth.builder()
            .accountId(accountIdFrom)
            .email(emailFrom)
            .apiKey(cloudflareGlobalKeyFrom)
            .build(),
        ACloudflareAuth.builder()
            .accountId(accountIdTo)
            .email(emailto)
            .apiKey(cloudflareGlobalKeyTo)
            .build(),domain);
  }
}
