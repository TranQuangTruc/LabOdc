package com.labodc.userprofile.controller;

import com.labodc.userprofile.dto.profile.MentorProfileRequest;
import com.labodc.userprofile.entity.MentorProfile;
import com.labodc.userprofile.service.MentorProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mentors")
@Slf4j
@CrossOrigin(origins = "*")
public class MentorProfileController {
    
    @Autowired
    private MentorProfileService mentorProfileService;
    
    @PostMapping("/{userId}/profile")
    @PreAuthorize("hasAuthority('MENTOR')")
    public ResponseEntity<MentorProfile> createOrUpdateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody MentorProfileRequest request) {
        log.info("Create/Update mentor profile for user: {}", userId);
        MentorProfile profile = mentorProfileService.createOrUpdateProfile(userId, request);
        return ResponseEntity.ok(profile);
    }
    
    @GetMapping("/{userId}/profile")
    public ResponseEntity<MentorProfile> getProfile(@PathVariable Long userId) {
        log.info("Get mentor profile for user: {}", userId);
        MentorProfile profile = mentorProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
    
    @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MENTOR')")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        log.info("Upload avatar for user: {}", userId);
        try {
            String avatarUrl = mentorProfileService.uploadAvatar(userId, file);
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", avatarUrl);
            response.put("message", "Avatar uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Failed to upload avatar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload avatar"));
        }
    }
    
    @DeleteMapping("/{userId}/profile")
    @PreAuthorize("hasAuthority('MENTOR')")
    public ResponseEntity<Map<String, String>> deleteProfile(@PathVariable Long userId) {
        log.info("Delete mentor profile for user: {}", userId);
        mentorProfileService.deleteProfile(userId);
        return ResponseEntity.ok(Map.of("message", "Profile deleted successfully"));
    }
}