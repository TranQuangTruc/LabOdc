package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.ProjectRequest;
import com.TaskAndReporting_service.dto.response.ProjectResponse;
import com.TaskAndReporting_service.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    ProjectService projectService;

    @PostMapping("")
    public ApiResponse<ProjectResponse> createProject(@RequestBody ProjectRequest request){
        return ApiResponse.<ProjectResponse>builder().build();
    }
}
