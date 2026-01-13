package com.labodc.userprofile.service;


import com.labodc.userprofile.dto.auth.AuthResponse;
import com.labodc.userprofile.dto.auth.LoginRequest;
import com.labodc.userprofile.dto.auth.RegisterRequest;
import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.repository.UserRepository;
import com.labodc.userprofile.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final JwtTokenProvider jwtTokenProvider;
private final AuthenticationManager authenticationManager;


public AuthService(UserRepository userRepository,
PasswordEncoder passwordEncoder,
JwtTokenProvider jwtTokenProvider,
AuthenticationManager authenticationManager) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtTokenProvider = jwtTokenProvider;
this.authenticationManager = authenticationManager;
}


// Dang ky tai khoan moi
public void register(RegisterRequest request) {
if (userRepository.existsByUsername(request.getUsername())) {
throw new RuntimeException("Username da ton tai");
}


User user = new User();
user.setUsername(request.getUsername());
user.setPassword(passwordEncoder.encode(request.getPassword()));
user.setRole(request.getRole());


userRepository.save(user);
}


// Dang nhap va tao JWT
public AuthResponse login(LoginRequest request) {
authenticationManager.authenticate(
new UsernamePasswordAuthenticationToken(
request.getUsername(),
request.getPassword()
)
);


String token = jwtTokenProvider.generateToken(request.getUsername());
return new AuthResponse(token);
}
}