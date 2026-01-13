package com.labodc.userprofile.repository.profile;


import com.labodc.userprofile.entity.profile.TalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface TalentProfileRepository extends JpaRepository<TalentProfile, Long> {
Optional<TalentProfile> findByUserId(Long userId);
}