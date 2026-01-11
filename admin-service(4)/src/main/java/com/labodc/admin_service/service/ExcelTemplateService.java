package com.labodc.admin_service.service;

import com.labodc.admin_service.entity.ExcelTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ExcelTemplateService {

    ExcelTemplate upload(
        String name,
        String description,
        String version,
        MultipartFile file
    );

    List<ExcelTemplate> getAll();

    ExcelTemplate getActiveTemplate(String name);

    void deactivate(UUID id);
}
