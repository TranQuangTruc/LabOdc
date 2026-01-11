package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.ExcelTemplate;
import com.labodc.admin_service.entity.TemplateStatus;
import com.labodc.admin_service.exception.BusinessException;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.repository.ExcelTemplateRepository;
import com.labodc.admin_service.service.ExcelTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcelTemplateServiceImpl implements ExcelTemplateService {

    private static final String STORAGE_DIR = "/data/templates/";

    private final ExcelTemplateRepository repo;

    {
        new File(STORAGE_DIR).mkdirs();
    }

    @Override
    public ExcelTemplate upload(String name, String description, String version, MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(STORAGE_DIR + filename);
            Files.copy(file.getInputStream(), dest.toPath());

            ExcelTemplate template = new ExcelTemplate();
            template.setName(name);
            template.setDescription(description);
            template.setVersion(version);
            template.setFilePath(dest.getAbsolutePath());
            template.setStatus(TemplateStatus.ACTIVE);

            return repo.save(template);
        } catch (Exception e) {
            throw new BusinessException("Upload template failed");
        }
    }

    @Override
    public List<ExcelTemplate> getAll() {
        return repo.findAll();
    }

    @Override
    public ExcelTemplate getActiveTemplate(String name) {
        return repo.findByNameAndStatus(name, TemplateStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("No active template found"));
    }

    @Override
    public void deactivate(UUID id) {
        ExcelTemplate template = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Template not found"));

        template.setStatus(TemplateStatus.INACTIVE);
        repo.save(template);
    }
}
