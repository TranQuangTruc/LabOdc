package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.TalentResponse; 
import com.labodc.admin_service.entity.Talent;
import com.labodc.admin_service.mapper.TalentMapper; 
import com.labodc.admin_service.service.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/talents")
@RequiredArgsConstructor
public class TalentAdminController {

    private final TalentService service;
    private final TalentMapper mapper; 

    @GetMapping
    public ApiResponse<List<TalentResponse>> getAll() { 
        List<TalentResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse) 
                .toList();
        return ApiResponse.success(responseList); 
    }

    @GetMapping("/{id}")
    public ApiResponse<TalentResponse> getDetail(@PathVariable UUID id) { 
        Talent talent = service.getById(id);
        return ApiResponse.success(mapper.toResponse(talent)); 
    }

    @PostMapping("/{id}/suspend")
    public ApiResponse<Void> suspend(@PathVariable UUID id) {
        service.suspend(id);
        return ApiResponse.success(null); 
    }

    @PostMapping("/{id}/activate")
    public ApiResponse<Void> activate(@PathVariable UUID id) {
        service.activate(id);
        return ApiResponse.success(null); 
    }
}
