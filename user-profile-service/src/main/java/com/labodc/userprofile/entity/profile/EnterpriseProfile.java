package com.labodc.userprofile.entity.profile;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enterprise_profiles", uniqueConstraints = {
        @UniqueConstraint(name = "uk_enterprise_user_id", columnNames = "user_id")
})
public class EnterpriseProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String companyName;
    private String taxCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    @Column(nullable = false)
    private boolean verified = false;

    private String rejectedReason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public EnterpriseProfile() {}

    // Getters/Setters
    public Long getId() { return id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getTaxCode() { return taxCode; }
    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getRejectedReason() { return rejectedReason; }
    public void setRejectedReason(String rejectedReason) { this.rejectedReason = rejectedReason; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
