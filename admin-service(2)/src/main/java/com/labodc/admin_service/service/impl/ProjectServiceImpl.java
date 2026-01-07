package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.*;
import com.labodc.admin_service.repository.ProjectRepository;
import com.labodc.admin_service.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project getById(UUID id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public void approve(UUID projectId) {
        Project project = getById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new RuntimeException("Project not in SUBMITTED state");
        }

        Enterprise enterprise = project.getEnterprise();
        if (enterprise.getStatus() != EnterpriseStatus.APPROVED) {
            throw new RuntimeException("Enterprise is not approved");
        }

        project.setStatus(ProjectStatus.APPROVED);
        projectRepository.save(project);
    }

    @Override
    public void reject(UUID projectId, String reason) {
        Project project = getById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new RuntimeException("Only SUBMITTED project can be rejected");
        }

        project.setStatus(ProjectStatus.REJECTED);
        projectRepository.save(project);

        // reason để log hoặc gửi event sau
    }

    @Override
    public void cancel(UUID projectId) {
        Project project = getById(projectId);
        project.setStatus(ProjectStatus.CANCELLED);
        projectRepository.save(project);
    }
}
