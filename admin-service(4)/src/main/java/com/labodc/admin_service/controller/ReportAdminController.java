package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.request.PublishReportRequest;
import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.ReportResponse;
import com.labodc.admin_service.entity.Report;
import com.labodc.admin_service.mapper.ReportMapper;
import com.labodc.admin_service.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportAdminController {

    private final ReportService service;
    private final ReportMapper mapper;

    @PostMapping
    public ApiResponse<ReportResponse> create(
            @Valid @RequestBody PublishReportRequest request
    ) {
        Report report = service.create(request);
        return ApiResponse.success(mapper.toResponse(report));
    }

    @GetMapping
    public ApiResponse<List<ReportResponse>> getAll() {
        List<ReportResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ApiResponse.success(responseList);
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<Void> publish(@PathVariable UUID id) {
        service.publish(id);
        return ApiResponse.success(null); 
    }
}
