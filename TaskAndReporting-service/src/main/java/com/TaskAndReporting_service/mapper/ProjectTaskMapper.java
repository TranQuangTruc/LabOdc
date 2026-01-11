package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.ProjectTaskRequest;
import com.TaskAndReporting_service.dto.response.ProjectTaskResponse;
import com.TaskAndReporting_service.entity.ProjectTask;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectTaskMapper {
    ProjectTask toProjectTask(ProjectTaskRequest projectTaskRequest);
    ProjectTaskResponse toProjectTaskResponse(ProjectTask projectTask);
}
