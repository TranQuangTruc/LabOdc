package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.EnterpriseRequest;
import com.TaskAndReporting_service.dto.response.EnterpriseResponse;
import com.TaskAndReporting_service.entity.Enterprise;
import com.TaskAndReporting_service.mapper.EnterpriseMapper;
import com.TaskAndReporting_service.repository.EnterpriseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnterpriseService {
    EnterpriseRepository enterpriseRepository;
    EnterpriseMapper enterpriseMapper;

    public EnterpriseResponse createEnterprise(EnterpriseRequest request){
        Enterprise enterprise = enterpriseMapper.toEnterprise(request);
        return enterpriseMapper.toEnterpriseResponse(enterpriseRepository.save(enterprise));
    }
}
