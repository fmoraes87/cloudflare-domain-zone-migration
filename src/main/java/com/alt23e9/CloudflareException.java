package com.alt23e9;

public class CloudflareException  extends RuntimeException {

  public CloudflareException(final String message) {
    super(message);
  }

  public CloudflareException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public CloudflareException(final Throwable cause) {

    super(cause);
  }

}
