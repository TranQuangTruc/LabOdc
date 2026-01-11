package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.*;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.exception.BusinessException;
import com.labodc.admin_service.repository.ProjectRepository;
import com.labodc.admin_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project getById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    @Override
    public void approve(UUID projectId) {
        Project project = getById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new BusinessException("Project not in SUBMITTED state");
        }

        Enterprise enterprise = project.getEnterprise();
        if (enterprise.getStatus() != EnterpriseStatus.APPROVED) {
            throw new BusinessException("Enterprise is not approved");
        }

        project.setStatus(ProjectStatus.APPROVED);
        projectRepository.save(project);
    }

    @Override
    public void reject(UUID projectId, String reason) {
        Project project = getById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new BusinessException("Only SUBMITTED project can be rejected");
        }

        project.setStatus(ProjectStatus.REJECTED);
        projectRepository.save(project);
    }

    @Override
    public void cancel(UUID projectId) {
        Project project = getById(projectId);
        project.setStatus(ProjectStatus.CANCELLED);
        projectRepository.save(project);
    }
}
