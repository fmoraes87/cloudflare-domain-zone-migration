package com.alt23e9.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACloudflareAuth {

  String email;
  String apiKey;
  String accountId;

}
