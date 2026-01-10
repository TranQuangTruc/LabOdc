package com.labodc.payment_service.dto;

import java.math.BigDecimal;

public class FundDistributionDTO {

    private BigDecimal team;
    private BigDecimal mentor;
    private BigDecimal lab;

    public FundDistributionDTO() {
    }

    public BigDecimal getTeam() {
        return team;
    }

    public void setTeam(BigDecimal team) {
        this.team = team;
    }

    public BigDecimal getMentor() {
        return mentor;
    }

    public void setMentor(BigDecimal mentor) {
        this.mentor = mentor;
    }

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }
}
