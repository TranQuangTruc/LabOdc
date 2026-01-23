package com.labodc.auth.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InMemoryUserService {
  public record UserRecord(String username, String password, String role) {}

  private final Map<String, UserRecord> users = Map.of(
      "enterprise", new UserRecord("enterprise", "enterprise", "ROLE_ENTERPRISE"),
      "admin", new UserRecord("admin", "admin", "ROLE_LAB_ADMIN"),
      "mentor", new UserRecord("mentor", "mentor", "ROLE_MENTOR"),
      "talentlead", new UserRecord("talentlead", "talentlead", "ROLE_TALENT_LEADER")
  );

  public UserRecord authenticate(String username, String password) {
    var u = users.get(username);
    if (u == null) return null;
    if (!u.password().equals(password)) return null;
    return u;
  }
}
