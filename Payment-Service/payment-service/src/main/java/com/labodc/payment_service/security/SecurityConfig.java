package com.labodc.payment_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF cho REST API
            .csrf(csrf -> csrf.disable())

            // Bật CORS (đã cấu hình ở CorsConfig)
            .cors(Customizer.withDefaults())

            // Cho phép truy cập không cần login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/actuator/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Tắt login form
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
