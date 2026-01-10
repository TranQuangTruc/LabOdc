package com.labodc.payment_service.dto;

import java.util.UUID;

public class PaymentResponseDTO {

    private UUID paymentId;
    private FundDistributionDTO distribution;
    private String status;

    public PaymentResponseDTO() {
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public FundDistributionDTO getDistribution() {
        return distribution;
    }

    public void setDistribution(FundDistributionDTO distribution) {
        this.distribution = distribution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
