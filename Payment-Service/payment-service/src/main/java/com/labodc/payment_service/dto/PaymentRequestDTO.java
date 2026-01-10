package com.labodc.payment_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequestDTO {

    private UUID projectId;
    private UUID enterpriseId;
    private BigDecimal totalAmount;

    public PaymentRequestDTO() {
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
}
