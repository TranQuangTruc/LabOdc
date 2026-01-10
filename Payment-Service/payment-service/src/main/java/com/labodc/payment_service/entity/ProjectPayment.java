package com.labodc.payment_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_payments")
public class ProjectPayment {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID projectId;
    private UUID enterpriseId;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private boolean advancedByLab;

    private LocalDateTime createdAt;

    public ProjectPayment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public boolean isAdvancedByLab() {
        return advancedByLab;
    }

    public void setAdvancedByLab(boolean advancedByLab) {
        this.advancedByLab = advancedByLab;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
