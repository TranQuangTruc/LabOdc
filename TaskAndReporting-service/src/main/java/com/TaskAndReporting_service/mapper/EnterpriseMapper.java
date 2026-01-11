package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.EnterpriseRequest;
import com.TaskAndReporting_service.dto.response.EnterpriseResponse;
import com.TaskAndReporting_service.entity.Enterprise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {
    Enterprise toEnterprise(EnterpriseRequest enterpriseRequest);
    EnterpriseResponse toEnterpriseResponse(Enterprise enterprise);
}
