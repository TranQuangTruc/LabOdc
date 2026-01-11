package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.ProjectTaskRequest;
import com.TaskAndReporting_service.dto.response.ProjectTaskResponse;
import com.TaskAndReporting_service.service.ProjectTaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectTaskController {
    ProjectTaskService projectTaskService;

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("")
    public ApiResponse<ProjectTaskResponse> createTask(@RequestBody ProjectTaskRequest request){
        return ApiResponse.<ProjectTaskResponse>builder().build();
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/{projectId}/{talentId}")
    public ApiResponse<List<ProjectTaskResponse>> getMyTask(
            @PathVariable String projectId,
            @PathVariable String talentId
    ) {
        return ApiResponse.<List<ProjectTaskResponse>>builder().build();
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/{projectId}")
    public ApiResponse<List<ProjectTaskResponse>> getMyTaskForMentor(@PathVariable String projectId){
        return ApiResponse.<List<ProjectTaskResponse>>builder().build();
    }
}
