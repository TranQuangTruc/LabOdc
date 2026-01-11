package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.LabAdminRequest;
import com.TaskAndReporting_service.dto.response.LabAdminResponse;
import com.TaskAndReporting_service.entity.LabAdmin;
import com.TaskAndReporting_service.mapper.LabAdminMapper;
import com.TaskAndReporting_service.repository.LabAdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabAdminService {
    LabAdminRepository labAdminRepository;
    LabAdminMapper labAdminMapper;

    public LabAdminResponse createLabAdmin(LabAdminRequest request){
        LabAdmin labAdmin = labAdminMapper.toLabAdmin(request);
        return labAdminMapper.toLabAdminResponse(labAdminRepository.save(labAdmin));
    }
}
