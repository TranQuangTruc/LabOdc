package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.TalentRequest;
import com.TaskAndReporting_service.dto.response.TalentResponse;
import com.TaskAndReporting_service.entity.Talent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TalentMapper {
    Talent toTalent(TalentRequest talentRequest);
    TalentResponse toTalentResponse(Talent talent);
}
