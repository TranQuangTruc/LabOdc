package com.labodc.auth.controller;

import com.labodc.auth.dto.LoginRequest;
import com.labodc.auth.dto.LoginResponse;
import com.labodc.auth.security.JwtUtil;
import com.labodc.auth.service.InMemoryUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final InMemoryUserService users;
  private final JwtUtil jwt;

  public AuthController(InMemoryUserService users, JwtUtil jwt) {
    this.users = users;
    this.jwt = jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
    var u = users.authenticate(req.getUsername(), req.getPassword());
    if (u == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Map.of("message", "Invalid credentials"));
    }

    String token = jwt.generateToken(u.username(), u.role());
    return ResponseEntity.ok(new LoginResponse(token, u.username(), u.role()));
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(Authentication auth) {
    return ResponseEntity.ok(Map.of(
        "username", auth.getName(),
        "authorities", auth.getAuthorities().toString()
    ));
  }
}
