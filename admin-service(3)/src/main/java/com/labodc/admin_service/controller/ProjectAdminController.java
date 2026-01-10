package com.labodc.admin_service.controller;

import com.labodc.admin_service.entity.Project;
import com.labodc.admin_service.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/projects")
public class ProjectAdminController {

    private final ProjectService service;

    public ProjectAdminController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Project> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Project getDetail(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable UUID id) {
        service.approve(id);
    }

    @PostMapping("/{id}/reject")
    public void reject(
        @PathVariable UUID id,
        @RequestParam String reason
    ) {
        service.reject(id, reason);
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable UUID id) {
        service.cancel(id);
    }
}
