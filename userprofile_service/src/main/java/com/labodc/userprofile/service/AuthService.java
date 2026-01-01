package com.labodc.userprofile.service;

import com.labodc.userprofile.dto.auth.AuthResponse;
import com.labodc.userprofile.dto.auth.LoginRequest;
import com.labodc.userprofile.dto.auth.RegisterRequest;
import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.entity.UserRole;
import com.labodc.userprofile.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private TokenCacheService tokenCacheService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        // Validate role (only TALENT and MENTOR can register via this endpoint)
        if (request.getRole() != UserRole.TALENT && request.getRole() != UserRole.MENTOR) {
            throw new IllegalArgumentException("Invalid role for registration");
        }
        
        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .isActive(true)
                .emailVerified(false)
                .build();
        
        user = userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Cache token in Redis
        tokenCacheService.cacheToken(user.getEmail(), token, 1440); // 24 hours
        
        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .hasProfile(false)
                .build();
    }
    
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());
        
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // Get user details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        
        if (!user.getIsActive()) {
            throw new IllegalArgumentException("Account is deactivated");
        }
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Cache token in Redis
        tokenCacheService.cacheToken(user.getEmail(), token, 1440);
        
        // Check if user has profile
        boolean hasProfile = false;
        if (user.getRole() == UserRole.TALENT) {
            hasProfile = user.getTalentProfile() != null;
        } else if (user.getRole() == UserRole.MENTOR) {
            hasProfile = user.getMentorProfile() != null;
        }
        
        log.info("User logged in successfully: {}", user.getEmail());
        
        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .hasProfile(hasProfile)
                .build();
    }
    
    public void logout(String username, String token) {
        log.info("User logout: {}", username);
        tokenCacheService.invalidateToken(username);
        tokenCacheService.blacklistToken(token, 1440);
    }
}