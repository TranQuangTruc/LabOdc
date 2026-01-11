package com.labodc.userprofile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mentor_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(length = 200)
    private String company;
    
    @Column(length = 100)
    private String jobTitle;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer yearsOfExperience = 0;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    @ElementCollection
    @CollectionTable(name = "mentor_expertise", joinColumns = @JoinColumn(name = "mentor_profile_id"))
    @Column(name = "expertise")
    @Builder.Default
    private List<String> expertiseAreas = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "mentor_certifications", joinColumns = @JoinColumn(name = "mentor_profile_id"))
    @Column(name = "certification")
    @Builder.Default
    private List<String> certifications = new ArrayList<>();
    
    @Column(length = 500)
    private String linkedinUrl;
    
    @Column(length = 500)
    private String portfolioUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isVerified = false;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}