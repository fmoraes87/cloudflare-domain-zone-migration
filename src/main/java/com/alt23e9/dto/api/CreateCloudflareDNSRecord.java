package com.alt23e9.dto.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCloudflareDNSRecord {

  String content;
  String name;
  Boolean proxied;
  String type;
  String comment;
  int ttl;

}
