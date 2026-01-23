package com.labodc.userprofile.repository.profile;

import com.labodc.userprofile.entity.profile.EnterpriseProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnterpriseProfileRepository extends JpaRepository<EnterpriseProfile, Long> {
    Optional<EnterpriseProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
