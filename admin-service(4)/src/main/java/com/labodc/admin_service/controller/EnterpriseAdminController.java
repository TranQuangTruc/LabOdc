package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.EnterpriseResponse;
import com.labodc.admin_service.entity.Enterprise;
import com.labodc.admin_service.mapper.EnterpriseMapper;
import com.labodc.admin_service.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/enterprises")
@RequiredArgsConstructor
public class EnterpriseAdminController {

    private final EnterpriseService service;
    private final EnterpriseMapper mapper;

    @GetMapping
    public ApiResponse<List<EnterpriseResponse>> getAll() {
        List<EnterpriseResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ApiResponse.success(responseList);
    }

    @GetMapping("/{id}")
    public ApiResponse<EnterpriseResponse> getDetail(@PathVariable UUID id) {
        Enterprise enterprise = service.getById(id);
        return ApiResponse.success(mapper.toResponse(enterprise));
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<Void> approve(@PathVariable UUID id) {
        service.approve(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/suspend")
    public ApiResponse<Void> suspend(@PathVariable UUID id) {
        service.suspend(id);
        return ApiResponse.success(null);
    }
}
