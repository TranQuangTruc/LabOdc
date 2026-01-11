package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.LabAdminRequest;
import com.TaskAndReporting_service.dto.response.LabAdminResponse;
import com.TaskAndReporting_service.entity.LabAdmin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabAdminMapper {
    LabAdmin toLabAdmin(LabAdminRequest labAdminRequest);
    LabAdminResponse toLabAdminResponse(LabAdmin labAdmin);
}
