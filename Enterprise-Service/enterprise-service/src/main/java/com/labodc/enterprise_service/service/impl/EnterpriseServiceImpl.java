package com.labodc.enterprise_service.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.labodc.enterprise_service.dto.EnterpriseLoginRequest;
import com.labodc.enterprise_service.dto.EnterpriseProfileResponse;
import com.labodc.enterprise_service.dto.EnterpriseRegisterRequest;
import com.labodc.enterprise_service.entity.Enterprise;
import com.labodc.enterprise_service.repository.EnterpriseRepository;
import com.labodc.enterprise_service.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository repository;
    private final PasswordEncoder passwordEncoder;

    public EnterpriseServiceImpl(
            EnterpriseRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Enterprise register(EnterpriseRegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setCompanyName(request.getCompanyName());
        enterprise.setEmail(request.getEmail());
        enterprise.setPassword(passwordEncoder.encode(request.getPassword()));
        enterprise.setAddress(request.getAddress());
        enterprise.setDescription(request.getDescription());

        return repository.save(enterprise);
    }

    @Override
    public Enterprise login(EnterpriseLoginRequest request) {
        Enterprise enterprise = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(), enterprise.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return enterprise;
    }

    @Override
    public EnterpriseProfileResponse getProfile(Long enterpriseId) {
        Enterprise enterprise = repository.findById(enterpriseId)
                .orElseThrow(() -> new RuntimeException("Enterprise not found"));

        EnterpriseProfileResponse response = new EnterpriseProfileResponse();
        response.setId(enterprise.getId());
        response.setCompanyName(enterprise.getCompanyName());
        response.setEmail(enterprise.getEmail());
        response.setAddress(enterprise.getAddress());
        response.setDescription(enterprise.getDescription());

        return response;
    }
}
