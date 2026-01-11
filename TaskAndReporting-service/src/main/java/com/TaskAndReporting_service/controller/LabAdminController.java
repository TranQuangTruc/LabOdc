package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.LabAdminRequest;
import com.TaskAndReporting_service.dto.response.LabAdminResponse;
import com.TaskAndReporting_service.service.LabAdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labAdmin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabAdminController {
    LabAdminService labAdminService;

    @PostMapping("")
    public ApiResponse<LabAdminResponse> createLabAdmin(@RequestBody LabAdminRequest request){
        return ApiResponse.<LabAdminResponse>builder().build();
    }
}
