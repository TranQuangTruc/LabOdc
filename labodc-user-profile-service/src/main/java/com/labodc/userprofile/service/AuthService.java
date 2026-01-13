package com.labodc.userprofile.service;

import com.labodc.userprofile.dto.auth.AuthResponse;
import com.labodc.userprofile.dto.auth.LoginRequest;
import com.labodc.userprofile.dto.auth.RegisterRequest;
import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.repository.UserRepository;
import com.labodc.userprofile.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Dang ky
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

    //  LOGIN KHÔNG DÙNG AuthenticationManager
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Sai username hoac password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai username hoac password");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
