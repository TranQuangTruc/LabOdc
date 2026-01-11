package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.response.TemplateResponse;
import com.labodc.admin_service.entity.ExcelTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExcelTemplateMapper {

    TemplateResponse toResponse(ExcelTemplate entity);

}
