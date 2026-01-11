package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.Enterprise;
import com.labodc.admin_service.entity.EnterpriseStatus;
import com.labodc.admin_service.exception.BusinessException;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.repository.EnterpriseRepository;
import com.labodc.admin_service.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository repository;

    @Override
    public List<Enterprise> getAll() {
        return repository.findAll();
    }

    @Override
    public Enterprise getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enterprise not found"));
    }

    @Override
    public void approve(UUID id) {
        Enterprise enterprise = getById(id);

        if (enterprise.getStatus() != EnterpriseStatus.PENDING) {
            throw new BusinessException("Enterprise not in PENDING state");
        }

        enterprise.setStatus(EnterpriseStatus.APPROVED);
        enterprise.setVerified(true);
        repository.save(enterprise);
    }

    @Override
    public void suspend(UUID id) {
        Enterprise enterprise = getById(id);
        enterprise.setStatus(EnterpriseStatus.SUSPENDED);
        repository.save(enterprise);
    }
}
