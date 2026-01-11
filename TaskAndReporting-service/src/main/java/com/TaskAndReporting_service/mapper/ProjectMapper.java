package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.ProjectRequest;
import com.TaskAndReporting_service.dto.response.ProjectResponse;
import com.TaskAndReporting_service.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest projectRequest);
    ProjectResponse toProjectResponse(Project project);
}
