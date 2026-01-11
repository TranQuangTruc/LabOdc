package com.labodc.userprofile.dto.profile;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentProfileRequest {
    
    @Size(max = 20, message = "Student ID must not exceed 20 characters")
    private String studentId;
    
    @Size(max = 100, message = "Major must not exceed 100 characters")
    private String major;
    
    @Size(max = 50, message = "Year of study must not exceed 50 characters")
    private String yearOfStudy;
    
    private LocalDate dateOfBirth;
    
    private String bio;
    
    private List<String> skills;
    
    private List<String> certifications;
    
    private List<String> portfolioUrls;
    
    @Size(max = 500, message = "GitHub URL must not exceed 500 characters")
    private String githubUrl;
    
    @Size(max = 500, message = "LinkedIn URL must not exceed 500 characters")
    private String linkedinUrl;
    
    @Size(max = 500, message = "CV URL must not exceed 500 characters")
    private String cvUrl;
}