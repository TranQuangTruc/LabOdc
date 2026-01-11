package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.MentorRequest;
import com.TaskAndReporting_service.dto.response.MentorResponse;
import com.TaskAndReporting_service.entity.Mentor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MentorMapper {
    Mentor toMentor(MentorRequest mentorRequest);
    MentorResponse toMentorResponse(Mentor mentor);
}
