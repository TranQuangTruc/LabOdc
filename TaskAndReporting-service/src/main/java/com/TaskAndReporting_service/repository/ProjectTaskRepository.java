package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, String> {
    List<ProjectTask> findByProjectIdAndTalentIdsContaining(String projectId, String talentId);
    List<ProjectTask> findByProjectId(String projectId);
}
