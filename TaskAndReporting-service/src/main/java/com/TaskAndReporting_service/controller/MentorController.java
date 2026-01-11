package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.MentorRequest;
import com.TaskAndReporting_service.dto.response.MentorResponse;
import com.TaskAndReporting_service.service.MentorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mentor")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorController {
    MentorService mentorService;

    @PostMapping("")
    public ApiResponse<MentorResponse> createMentor(@RequestBody MentorRequest request){
        return ApiResponse.<MentorResponse>builder().build();
    }
}
