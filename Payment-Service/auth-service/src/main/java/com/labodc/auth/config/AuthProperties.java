package com.labodc.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "labodc.auth")
public class AuthProperties {
  /** HMAC secret for signing JWT. Keep in env var in prod. */
  private String jwtSecret = "dev-secret-change-me";

  /** Token TTL in seconds. */
  private long jwtTtlSeconds = 3600;

  public String getJwtSecret() {
    return jwtSecret;
  }

  public void setJwtSecret(String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public long getJwtTtlSeconds() {
    return jwtTtlSeconds;
  }

  public void setJwtTtlSeconds(long jwtTtlSeconds) {
    this.jwtTtlSeconds = jwtTtlSeconds;
  }
}
