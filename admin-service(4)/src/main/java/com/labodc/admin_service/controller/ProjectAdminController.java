package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.ProjectResponse;
import com.labodc.admin_service.entity.Project;
import com.labodc.admin_service.mapper.ProjectMapper; 
import com.labodc.admin_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/projects")
@RequiredArgsConstructor
public class ProjectAdminController {

    private final ProjectService service;
    private final ProjectMapper mapper;

    @GetMapping
    public ApiResponse<List<ProjectResponse>> getAll() {
        List<ProjectResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ApiResponse.success(responseList);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponse> getDetail(@PathVariable UUID id) {
        Project project = service.getById(id);
        return ApiResponse.success(mapper.toResponse(project)); 
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<Void> approve(@PathVariable UUID id) {
        service.approve(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/reject")
    public ApiResponse<Void> reject(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        service.reject(id, reason);
        return ApiResponse.success(null); 
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<Void> cancel(@PathVariable UUID id) {
        service.cancel(id);
        return ApiResponse.success(null);
    }
}
