package com.alt23e9.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACloudflareZone {

  ACloudflareAuth auth;

  String id;

  String name;

}