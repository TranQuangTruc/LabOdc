package com.labodc.userprofile.repository.profile;

import com.labodc.userprofile.entity.profile.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {
    Optional<MentorProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
