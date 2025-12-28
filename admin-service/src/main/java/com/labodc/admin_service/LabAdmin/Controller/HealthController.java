package com.labodc.admin_service.LabAdmin.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/health")
public class HealthController {

    @GetMapping
    public String health() {
        return "LAB ADMIN OK";
    }
}
