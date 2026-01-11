package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.request.CreateEnterpriseRequest;
import com.labodc.admin_service.dto.response.EnterpriseResponse;
import com.labodc.admin_service.entity.Enterprise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {

    Enterprise toEntity(CreateEnterpriseRequest request);

    EnterpriseResponse toResponse(Enterprise entity);
}
