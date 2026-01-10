package com.labodc.admin_service.controller;

import com.labodc.admin_service.entity.Enterprise;
import com.labodc.admin_service.service.EnterpriseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/enterprises")
public class EnterpriseAdminController {

    private final EnterpriseService service;

    public EnterpriseAdminController(EnterpriseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Enterprise> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Enterprise getDetail(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable UUID id) {
        service.approve(id);
    }

    @PostMapping("/{id}/suspend")
    public void suspend(@PathVariable UUID id) {
        service.suspend(id);
    }
}
