package com.labodc.enterprise_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labodc.enterprise_service.entity.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    Optional<Enterprise> findByEmail(String email);

    boolean existsByEmail(String email);
}
