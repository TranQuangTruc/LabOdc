package com.labodc.userprofile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talent_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(length = 20)
    private String studentId;
    
    @Column(length = 100)
    private String major;
    
    @Column(length = 50)
    private String yearOfStudy;
    
    private LocalDate dateOfBirth;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    @ElementCollection
    @CollectionTable(name = "talent_skills", joinColumns = @JoinColumn(name = "talent_profile_id"))
    @Column(name = "skill")
    @Builder.Default
    private List<String> skills = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "talent_certifications", joinColumns = @JoinColumn(name = "talent_profile_id"))
    @Column(name = "certification")
    @Builder.Default
    private List<String> certifications = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "talent_portfolio", joinColumns = @JoinColumn(name = "talent_profile_id"))
    @Column(name = "portfolio_url")
    @Builder.Default
    private List<String> portfolioUrls = new ArrayList<>();
    
    @Column(length = 500)
    private String githubUrl;
    
    @Column(length = 500)
    private String linkedinUrl;
    
    @Column(length = 500)
    private String cvUrl;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}