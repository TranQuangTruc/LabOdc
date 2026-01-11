package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.MentorResponse; 
import com.labodc.admin_service.entity.Mentor;
import com.labodc.admin_service.mapper.MentorMapper; 
import com.labodc.admin_service.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/mentors")
@RequiredArgsConstructor
public class MentorAdminController {

    private final MentorService service;
    private final MentorMapper mapper;

    @GetMapping
    public ApiResponse<List<MentorResponse>> getAll() {
        List<MentorResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse) 
                .toList();
        return ApiResponse.success(responseList);
    }

    @GetMapping("/{id}")
    public ApiResponse<MentorResponse> getDetail(@PathVariable UUID id) {
        Mentor mentor = service.getById(id);
        return ApiResponse.success(mapper.toResponse(mentor)); 
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
