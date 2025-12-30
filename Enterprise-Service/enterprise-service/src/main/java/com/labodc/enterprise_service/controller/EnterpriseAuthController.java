package com.labodc.enterprise_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labodc.enterprise_service.dto.EnterpriseLoginRequest;
import com.labodc.enterprise_service.dto.EnterpriseProfileResponse;
import com.labodc.enterprise_service.dto.EnterpriseRegisterRequest;
import com.labodc.enterprise_service.entity.Enterprise;
import com.labodc.enterprise_service.service.EnterpriseService;

@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseAuthController {

    private final EnterpriseService enterpriseService;

    public EnterpriseAuthController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("/register")
    public Enterprise register(@RequestBody EnterpriseRegisterRequest request) {
        return enterpriseService.register(request);
    }

    @PostMapping("/login")
    public Enterprise login(@RequestBody EnterpriseLoginRequest request) {
        return enterpriseService.login(request);
    }

    @GetMapping("/profile/{id}")
    public EnterpriseProfileResponse profile(@PathVariable Long id) {
        return enterpriseService.getProfile(id);
    }
}
