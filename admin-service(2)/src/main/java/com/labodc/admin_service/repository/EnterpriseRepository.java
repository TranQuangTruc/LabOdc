package com.labodc.admin_service.repository;

import com.labodc.admin_service.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
}
