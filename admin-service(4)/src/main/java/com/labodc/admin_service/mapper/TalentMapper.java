package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.response.TalentResponse;
import com.labodc.admin_service.entity.Talent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TalentMapper {

    TalentResponse toResponse(Talent entity);
}
