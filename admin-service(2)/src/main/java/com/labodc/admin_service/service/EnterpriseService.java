package com.labodc.admin_service.service;

import com.labodc.admin_service.entity.Enterprise;
import com.labodc.admin_service.entity.EnterpriseStatus;

import java.util.List;
import java.util.UUID;

public interface EnterpriseService {

    List<Enterprise> getAll();

    Enterprise getById(UUID id);

    void approve(UUID id);

    void suspend(UUID id);
}
