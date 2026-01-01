package com.labodc.userprofile.repository;

import com.labodc.userprofile.entity.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {
    
    Optional<MentorProfile> findByUserId(Long userId);
    
    @Query("SELECT mp FROM MentorProfile mp WHERE mp.isVerified = true AND mp.isAvailable = true")
    List<MentorProfile> findAvailableVerifiedMentors();
    
    @Query("SELECT mp FROM MentorProfile mp WHERE mp.yearsOfExperience >= :minYears")
    List<MentorProfile> findByMinimumExperience(@Param("minYears") Integer minYears);
    
    @Query("SELECT mp FROM MentorProfile mp JOIN mp.expertiseAreas ea WHERE ea IN :expertise")
    List<MentorProfile> findByExpertiseIn(@Param("expertise") List<String> expertise);
    
    @Query("SELECT mp FROM MentorProfile mp WHERE mp.user.isActive = true")
    List<MentorProfile> findAllActiveProfiles();
    
    List<MentorProfile> findByIsVerifiedTrue();
    
    List<MentorProfile> findByIsAvailableTrue();
}