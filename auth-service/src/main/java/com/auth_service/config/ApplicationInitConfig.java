package com.auth_service.config;

import java.util.Date;
import java.util.HashSet;

import com.auth_service.constant.PredefinedRole;
import com.auth_service.entity.Role;
import com.auth_service.entity.User;
import com.auth_service.repository.RoleRepository;
import com.auth_service.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.TALENT_ROLE)
                        .description("Talent role")
                        .build());

                roleRepository.save(Role.builder()
                        .name(PredefinedRole.MENTOR_ROLE)
                        .description("Mentor role")
                        .build());

                roleRepository.save(Role.builder()
                        .name(PredefinedRole.ENTERPRISE_ROLE)
                        .description("Enterprise role")
                        .build());

                roleRepository.save(Role.builder()
                        .name(PredefinedRole.LAB_ADMIN_ROLE)
                        .description("Lab admin role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .createAt(new Date())
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
