package com.labodc.userprofile.controller;


import com.labodc.userprofile.dto.auth.LoginRequest;
import com.labodc.userprofile.dto.auth.RegisterRequest;
import com.labodc.userprofile.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


private final AuthService authService;


public AuthController(AuthService authService) {
this.authService = authService;
}


@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
authService.register(request);
return ResponseEntity.ok("Dang ky thanh cong");
}


@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
return ResponseEntity.ok(authService.login(request));
}
}