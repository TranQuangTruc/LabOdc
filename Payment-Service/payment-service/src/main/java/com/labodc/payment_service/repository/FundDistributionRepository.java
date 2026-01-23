package com.labodc.payment_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labodc.payment_service.entity.FundDistribution;

public interface FundDistributionRepository extends JpaRepository<FundDistribution, UUID> {

    FundDistribution findByPaymentId(UUID paymentId);
}
