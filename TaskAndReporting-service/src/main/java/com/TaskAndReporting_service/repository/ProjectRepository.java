package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.Enterprise;
import com.TaskAndReporting_service.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
