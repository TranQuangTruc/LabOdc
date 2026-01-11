package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.MentorRequest;
import com.TaskAndReporting_service.dto.response.MentorResponse;
import com.TaskAndReporting_service.entity.Mentor;
import com.TaskAndReporting_service.mapper.MentorMapper;
import com.TaskAndReporting_service.repository.MentorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorService {
    MentorRepository mentorRepository;
    MentorMapper mentorMapper;

    public MentorResponse createMentor(MentorRequest request){
        Mentor mentor = mentorMapper.toMentor(request);
        return mentorMapper.toMentorResponse(mentorRepository.save(mentor));
    }
}
