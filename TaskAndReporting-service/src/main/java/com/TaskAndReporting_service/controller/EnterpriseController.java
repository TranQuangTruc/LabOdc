package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.EnterpriseRequest;
import com.TaskAndReporting_service.dto.response.EnterpriseResponse;
import com.TaskAndReporting_service.service.EnterpriseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enterprise")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnterpriseController {
    EnterpriseService enterpriseService;

    @PostMapping("")
    public ApiResponse<EnterpriseResponse> createEnterprise(@RequestBody EnterpriseRequest request){
        return ApiResponse.<EnterpriseResponse>builder().build();
    }
}
