package com.labodc.project_service.repository;

import com.labodc.project_service.entity.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, String> {
    List<ProjectApplication> findByTalentId(String talentId);
}