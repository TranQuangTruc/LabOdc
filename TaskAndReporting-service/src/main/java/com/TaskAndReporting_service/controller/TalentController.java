package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.TalentRequest;
import com.TaskAndReporting_service.dto.response.TalentResponse;
import com.TaskAndReporting_service.service.TalentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talent")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TalentController {
    TalentService talentService;

    @PostMapping("")
    public ApiResponse<TalentResponse> createTalent(@RequestBody TalentRequest request){
        return ApiResponse.<TalentResponse>builder().build();
    }
}
