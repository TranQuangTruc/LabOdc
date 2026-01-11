package com.labodc.admin_service.mapper;

import com.labodc.admin_service.dto.request.CreateMentorRequest;
import com.labodc.admin_service.dto.response.MentorResponse;
import com.labodc.admin_service.entity.Mentor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MentorMapper {

    Mentor toEntity(CreateMentorRequest request);

    MentorResponse toResponse(Mentor entity);
}
