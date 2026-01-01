package com.labodc.userprofile.dto.profile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfileRequest {
    
    @Size(max = 200, message = "Company name must not exceed 200 characters")
    private String company;
    
    @Size(max = 100, message = "Job title must not exceed 100 characters")
    private String jobTitle;
    
    @Min(value = 0, message = "Years of experience must be non-negative")
    private Integer yearsOfExperience;
    
    private String bio;
    
    private List<String> expertiseAreas;
    
    private List<String> certifications;
    
    @Size(max = 500, message = "LinkedIn URL must not exceed 500 characters")
    private String linkedinUrl;
    
    @Size(max = 500, message = "Portfolio URL must not exceed 500 characters")
    private String portfolioUrl;
    
    private Boolean isAvailable;
}