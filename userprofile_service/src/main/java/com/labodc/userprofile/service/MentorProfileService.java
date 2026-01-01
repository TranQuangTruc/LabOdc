package com.labodc.userprofile.service;

import com.labodc.userprofile.dto.profile.MentorProfileRequest;
import com.labodc.userprofile.entity.MentorProfile;
import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.entity.UserRole;
import com.labodc.userprofile.exception.ResourceNotFoundException;
import com.labodc.userprofile.repository.MentorProfileRepository;
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
public class MentorProfileService {
    
    @Autowired
    private MentorProfileRepository mentorProfileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Transactional
    public MentorProfile createOrUpdateProfile(Long userId, MentorProfileRequest request) {
        log.info("Creating/Updating mentor profile for user: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (user.getRole() != UserRole.MENTOR) {
            throw new IllegalArgumentException("User is not a mentor");
        }
        
        MentorProfile profile = mentorProfileRepository.findByUserId(userId)
                .orElse(MentorProfile.builder()
                        .user(user)
                        .expertiseAreas(new ArrayList<>())
                        .certifications(new ArrayList<>())
                        .yearsOfExperience(0)
                        .isVerified(false)
                        .isAvailable(true)
                        .build());
        
        // Update profile fields
        if (request.getCompany() != null) {
            profile.setCompany(request.getCompany());
        }
        if (request.getJobTitle() != null) {
            profile.setJobTitle(request.getJobTitle());
        }
        if (request.getYearsOfExperience() != null) {
            profile.setYearsOfExperience(request.getYearsOfExperience());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getExpertiseAreas() != null) {
            profile.setExpertiseAreas(request.getExpertiseAreas());
        }
        if (request.getCertifications() != null) {
            profile.setCertifications(request.getCertifications());
        }
        if (request.getLinkedinUrl() != null) {
            profile.setLinkedinUrl(request.getLinkedinUrl());
        }
        if (request.getPortfolioUrl() != null) {
            profile.setPortfolioUrl(request.getPortfolioUrl());
        }
        if (request.getIsAvailable() != null) {
            profile.setIsAvailable(request.getIsAvailable());
        }
        
        profile = mentorProfileRepository.save(profile);
        log.info("Mentor profile saved successfully for user: {}", userId);
        
        return profile;
    }
    
    @Transactional(readOnly = true)
    public MentorProfile getProfile(Long userId) {
        return mentorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor profile not found"));
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
        log.info("Deleting mentor profile for user: {}", userId);
        
        MentorProfile profile = mentorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor profile not found"));
        
        mentorProfileRepository.delete(profile);
        log.info("Mentor profile deleted for user: {}", userId);
    }
}