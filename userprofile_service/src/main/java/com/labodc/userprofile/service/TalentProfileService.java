package com.labodc.userprofile.service;

import com.labodc.userprofile.dto.profile.TalentProfileRequest;
import com.labodc.userprofile.entity.TalentProfile;
import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.entity.UserRole;
import com.labodc.userprofile.exception.ResourceNotFoundException;
import com.labodc.userprofile.repository.TalentProfileRepository;
import com.labodc.userprofile.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
public class TalentProfileService {
    
    @Autowired
    private TalentProfileRepository talentProfileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Transactional
    public TalentProfile createOrUpdateProfile(Long userId, TalentProfileRequest request) {
        log.info("Creating/Updating talent profile for user: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (user.getRole() != UserRole.TALENT) {
            throw new IllegalArgumentException("User is not a talent");
        }
        
        TalentProfile profile = talentProfileRepository.findByUserId(userId)
                .orElse(TalentProfile.builder()
                        .user(user)
                        .skills(new ArrayList<>())
                        .certifications(new ArrayList<>())
                        .portfolioUrls(new ArrayList<>())
                        .build());
        
        // Update profile fields
        if (request.getStudentId() != null) {
            profile.setStudentId(request.getStudentId());
        }
        if (request.getMajor() != null) {
            profile.setMajor(request.getMajor());
        }
        if (request.getYearOfStudy() != null) {
            profile.setYearOfStudy(request.getYearOfStudy());
        }
        if (request.getDateOfBirth() != null) {
            profile.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getSkills() != null) {
            profile.setSkills(request.getSkills());
        }
        if (request.getCertifications() != null) {
            profile.setCertifications(request.getCertifications());
        }
        if (request.getPortfolioUrls() != null) {
            profile.setPortfolioUrls(request.getPortfolioUrls());
        }
        if (request.getGithubUrl() != null) {
            profile.setGithubUrl(request.getGithubUrl());
        }
        if (request.getLinkedinUrl() != null) {
            profile.setLinkedinUrl(request.getLinkedinUrl());
        }
        if (request.getCvUrl() != null) {
            profile.setCvUrl(request.getCvUrl());
        }
        
        profile = talentProfileRepository.save(profile);
        log.info("Talent profile saved successfully for user: {}", userId);
        
        return profile;
    }
    
    @Transactional(readOnly = true)
    public TalentProfile getProfile(Long userId) {
        return talentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent profile not found"));
    }
    
    @Transactional
    public String uploadAvatar(Long userId, MultipartFile file) throws IOException {
        log.info("Uploading avatar for user: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Delete old avatar if exists
        if (user.getAvatarUrl() != null) {
            cloudinaryService.deleteAvatar(user.getAvatarUrl());
        }
        
        // Upload new avatar
        String avatarUrl = cloudinaryService.uploadAvatar(file, userId);
        
        // Update user avatar URL
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
        
        log.info("Avatar uploaded successfully for user: {}", userId);
        return avatarUrl;
    }
    
    @Transactional
    public void deleteProfile(Long userId) {
        log.info("Deleting talent profile for user: {}", userId);
        
        TalentProfile profile = talentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent profile not found"));
        
        talentProfileRepository.delete(profile);
        log.info("Talent profile deleted for user: {}", userId);
    }
}