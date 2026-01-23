package com.labodc.auth.security;

import com.labodc.auth.config.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {
  private final AuthProperties props;
  private final SecretKey key;

  public JwtUtil(AuthProperties props) {
    this.props = props;
    // Accept any length; in prod use 32+ chars.
    this.key = Keys.hmacShaKeyFor(props.getJwtSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username, String role) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(props.getJwtTtlSeconds());

    return Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(exp))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims parse(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
