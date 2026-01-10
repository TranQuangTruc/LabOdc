package com.labodc.admin_service.controller;

import com.labodc.admin_service.entity.ExcelTemplate;
import com.labodc.admin_service.service.ExcelTemplateService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/templates")
public class ExcelTemplateAdminController {

    private final ExcelTemplateService service;

    public ExcelTemplateAdminController(ExcelTemplateService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ExcelTemplate upload(
        @RequestParam String name,
        @RequestParam String version,
        @RequestParam(required = false) String description,
        @RequestParam MultipartFile file
    ) {
        return service.upload(name, description, version, file);
    }

    @GetMapping
    public List<ExcelTemplate> getAll() {
        return service.getAll();
    }

    @PostMapping("/{id}/deactivate")
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
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
