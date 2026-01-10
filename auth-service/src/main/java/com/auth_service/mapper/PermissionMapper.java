package com.auth_service.mapper;

import com.auth_service.dto.request.PermissionRequest;
import com.auth_service.dto.response.PermissionResponse;
import com.auth_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
