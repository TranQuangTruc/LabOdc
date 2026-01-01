package com.labodc.userprofile.controller;

import com.labodc.userprofile.dto.profile.TalentProfileRequest;
import com.labodc.userprofile.entity.TalentProfile;
import com.labodc.userprofile.service.TalentProfileService;
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
@RequestMapping("/api/talents")
@Slf4j
@CrossOrigin(origins = "*")
public class TalentProfileController {
    
    @Autowired
    private TalentProfileService talentProfileService;
    
    @PostMapping("/{userId}/profile")
    @PreAuthorize("hasAuthority('TALENT')")
    public ResponseEntity<TalentProfile> createOrUpdateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody TalentProfileRequest request) {
        log.info("Create/Update talent profile for user: {}", userId);
        TalentProfile profile = talentProfileService.createOrUpdateProfile(userId, request);
        return ResponseEntity.ok(profile);
    }
    
    @GetMapping("/{userId}/profile")
    public ResponseEntity<TalentProfile> getProfile(@PathVariable Long userId) {
        log.info("Get talent profile for user: {}", userId);
        TalentProfile profile = talentProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
    
    @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TALENT')")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        log.info("Upload avatar for user: {}", userId);
        try {
            String avatarUrl = talentProfileService.uploadAvatar(userId, file);
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
    @PreAuthorize("hasAuthority('TALENT')")
    public ResponseEntity<Map<String, String>> deleteProfile(@PathVariable Long userId) {
        log.info("Delete talent profile for user: {}", userId);
        talentProfileService.deleteProfile(userId);
        return ResponseEntity.ok(Map.of("message", "Profile deleted successfully"));
    }
}