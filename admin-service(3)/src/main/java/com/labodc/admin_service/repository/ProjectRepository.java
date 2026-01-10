package com.labodc.admin_service.repository;

import com.labodc.admin_service.entity.Project;
import com.labodc.admin_service.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByStatus(ProjectStatus status);
}
