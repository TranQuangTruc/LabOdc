package com.labodc.admin_service.service;

import com.labodc.admin_service.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    List<Project> getAll();

    Project getById(UUID id);

    void approve(UUID projectId);

    void reject(UUID projectId, String reason);

    void cancel(UUID projectId);
}
