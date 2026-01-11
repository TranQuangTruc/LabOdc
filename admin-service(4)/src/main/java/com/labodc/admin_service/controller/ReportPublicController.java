package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.ReportResponse;
import com.labodc.admin_service.mapper.ReportMapper;
import com.labodc.admin_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportPublicController {

    private final ReportService service;
    private final ReportMapper mapper; 

    @GetMapping
    public ApiResponse<List<ReportResponse>> getPublishedReports() { 
        List<ReportResponse> responseList = service.getPublished()
                .stream()
                .map(mapper::toResponse) 
                .toList();
        return ApiResponse.success(responseList); 
    }
}
