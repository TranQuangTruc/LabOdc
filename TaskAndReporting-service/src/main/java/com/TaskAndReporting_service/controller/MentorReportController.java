package com.TaskAndReporting_service.controller;

import com.TaskAndReporting_service.dto.ApiResponse;
import com.TaskAndReporting_service.dto.request.MentorReportRequest;
import com.TaskAndReporting_service.dto.response.MentorReportResponse;
import com.TaskAndReporting_service.service.MentorReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorReportController {
    MentorReportService mentorReportService;

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("")
    public ApiResponse<MentorReportResponse> createReport(@RequestBody MentorReportRequest request){
        return ApiResponse.<MentorReportResponse>builder().build();
    }
    @PreAuthorize("hasRole('LAB_ADMIN')")
    @GetMapping("/{projectId}")
    public ApiResponse<List<MentorReportResponse>> getReportByProjectId(@PathVariable String projectId){
        return ApiResponse.<List<MentorReportResponse>>builder().build();
    }
}
