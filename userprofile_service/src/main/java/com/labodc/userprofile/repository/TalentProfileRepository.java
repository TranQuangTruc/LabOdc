package com.labodc.userprofile.repository;

import com.labodc.userprofile.entity.TalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalentProfileRepository extends JpaRepository<TalentProfile, Long> {
    
    Optional<TalentProfile> findByUserId(Long userId);
    
    Optional<TalentProfile> findByStudentId(String studentId);
    
    boolean existsByStudentId(String studentId);
    
    @Query("SELECT tp FROM TalentProfile tp WHERE tp.major = :major")
    List<TalentProfile> findByMajor(@Param("major") String major);
    
    @Query("SELECT tp FROM TalentProfile tp JOIN tp.skills s WHERE s IN :skills")
    List<TalentProfile> findBySkillsIn(@Param("skills") List<String> skills);
    
    @Query("SELECT tp FROM TalentProfile tp WHERE tp.user.isActive = true")
    List<TalentProfile> findAllActiveProfiles();
}