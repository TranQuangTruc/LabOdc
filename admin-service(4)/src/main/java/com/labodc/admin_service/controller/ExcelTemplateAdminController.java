package com.labodc.admin_service.controller;

import com.labodc.admin_service.dto.response.ApiResponse;
import com.labodc.admin_service.dto.response.TemplateResponse;
import com.labodc.admin_service.entity.ExcelTemplate;
import com.labodc.admin_service.mapper.ExcelTemplateMapper;
import com.labodc.admin_service.service.ExcelTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/templates")
@RequiredArgsConstructor
public class ExcelTemplateAdminController {

    private final ExcelTemplateService service;
    private final ExcelTemplateMapper mapper;

    @PostMapping("/upload")
    public ApiResponse<TemplateResponse> upload(
            @RequestParam String name,
            @RequestParam String version,
            @RequestParam(required = false) String description,
            @RequestParam MultipartFile file
    ) {
        ExcelTemplate template = service.upload(name, description, version, file);
        return ApiResponse.success(mapper.toResponse(template));
    }

    @GetMapping
    public ApiResponse<List<TemplateResponse>> getAll() {
        List<TemplateResponse> responseList = service.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ApiResponse.success(responseList);
    }

    @PostMapping("/{id}/deactivate")
    public ApiResponse<Void> deactivate(@PathVariable UUID id) {
        service.deactivate(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/active/{name}")
    public ResponseEntity<Resource> download(@PathVariable String name) {
        ExcelTemplate template = service.getActiveTemplate(name);
        FileSystemResource resource = new FileSystemResource(template.getFilePath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }
}
