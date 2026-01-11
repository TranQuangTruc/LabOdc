package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.request.PublishReportRequest;
import com.labodc.admin_service.dto.response.ReportResponse;
import com.labodc.admin_service.entity.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    Report toEntity(PublishReportRequest request);

    ReportResponse toResponse(Report entity);
}
