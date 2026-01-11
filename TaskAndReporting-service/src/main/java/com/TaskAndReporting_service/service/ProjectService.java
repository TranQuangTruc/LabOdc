package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.ProjectRequest;
import com.TaskAndReporting_service.dto.response.ProjectResponse;
import com.TaskAndReporting_service.entity.Project;
import com.TaskAndReporting_service.mapper.ProjectMapper;
import com.TaskAndReporting_service.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectService {
    ProjectRepository projectRepository;
    ProjectMapper projectMapper;

    public ProjectResponse createProject(ProjectRequest request){
        Project project = projectMapper.toProject(request);
        project.setCreateAt(Instant.now());
        return projectMapper.toProjectResponse(projectRepository.save(project));
    }
}
