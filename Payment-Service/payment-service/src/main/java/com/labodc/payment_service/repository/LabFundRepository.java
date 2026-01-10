package com.labodc.payment_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labodc.payment_service.entity.LabFund;

public interface LabFundRepository extends JpaRepository<LabFund, UUID> {
}
