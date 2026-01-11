package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.request.CreateProjectRequest;
import com.labodc.admin_service.dto.response.ProjectResponse;
import com.labodc.admin_service.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(CreateProjectRequest request);

    ProjectResponse toResponse(Project entity);
}
