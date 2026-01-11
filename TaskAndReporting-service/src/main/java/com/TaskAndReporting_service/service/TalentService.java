package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.TalentRequest;
import com.TaskAndReporting_service.dto.response.TalentResponse;
import com.TaskAndReporting_service.entity.Talent;
import com.TaskAndReporting_service.mapper.TalentMapper;
import com.TaskAndReporting_service.repository.TalentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TalentService {
    TalentRepository talentRepository;
    TalentMapper talentMapper;

    public TalentResponse createTalent(TalentRequest request){
        Talent talent = talentMapper.toTalent(request);
        return talentMapper.toTalentResponse(talentRepository.save(talent));
    }
}
