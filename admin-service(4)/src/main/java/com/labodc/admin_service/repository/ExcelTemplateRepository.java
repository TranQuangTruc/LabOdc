package com.labodc.admin_service.repository;

import com.labodc.admin_service.entity.ExcelTemplate;
import com.labodc.admin_service.entity.TemplateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExcelTemplateRepository extends JpaRepository<ExcelTemplate, UUID> {

    Optional<ExcelTemplate> findByNameAndStatus(String name, TemplateStatus status);
}
