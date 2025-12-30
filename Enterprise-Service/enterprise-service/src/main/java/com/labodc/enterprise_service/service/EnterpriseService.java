package com.labodc.enterprise_service.service;

import com.labodc.enterprise_service.dto.EnterpriseLoginRequest;
import com.labodc.enterprise_service.dto.EnterpriseProfileResponse;
import com.labodc.enterprise_service.dto.EnterpriseRegisterRequest;
import com.labodc.enterprise_service.entity.Enterprise;

public interface EnterpriseService {

    Enterprise register(EnterpriseRegisterRequest request);

    Enterprise login(EnterpriseLoginRequest request);

    EnterpriseProfileResponse getProfile(Long enterpriseId);
}
