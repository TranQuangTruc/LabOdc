package com.labodc.payment_service.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fund_distributions")
public class FundDistribution {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID paymentId;

    private BigDecimal teamAmount;
    private BigDecimal mentorAmount;
    private BigDecimal labAmount;

    public FundDistribution() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getTeamAmount() {
        return teamAmount;
    }

    public void setTeamAmount(BigDecimal teamAmount) {
        this.teamAmount = teamAmount;
    }

    public BigDecimal getMentorAmount() {
        return mentorAmount;
    }

    public void setMentorAmount(BigDecimal mentorAmount) {
        this.mentorAmount = mentorAmount;
    }

    public BigDecimal getLabAmount() {
        return labAmount;
    }

    public void setLabAmount(BigDecimal labAmount) {
        this.labAmount = labAmount;
    }

    public void setAmount(BigDecimal amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
