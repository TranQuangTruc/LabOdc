package com.labodc.userprofile.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtTokenProvider {


// Khoa bi mat dung de ky JWT
@Value("${jwt.secret}")
private String jwtSecret;


// Thoi gian het han cua token
@Value("${jwt.expiration}")
private long jwtExpiration;


// Tao token dua tren username
public String generateToken(String username) {
Date now = new Date();
Date expiryDate = new Date(now.getTime() + jwtExpiration);


return Jwts.builder()
.setSubject(username)
.setIssuedAt(now)
.setExpiration(expiryDate)
.signWith(SignatureAlgorithm.HS256, jwtSecret)
.compact();
}


// Lay username tu token
public String getUsernameFromToken(String token) {
Claims claims = Jwts.parser()
.setSigningKey(jwtSecret)
.parseClaimsJws(token)
.getBody();


return claims.getSubject();
}


// Kiem tra token hop le
public boolean validateToken(String token) {
try {
Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
return true;
} catch (Exception ex) {
return false;
}
}
}