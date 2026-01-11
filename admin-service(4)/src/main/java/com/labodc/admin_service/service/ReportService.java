package com.labodc.admin_service.service;

import com.labodc.admin_service.dto.request.PublishReportRequest;
import com.labodc.admin_service.entity.Report;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    Report create(PublishReportRequest request);

    List<Report> getAll();

    List<Report> getPublished();

    void publish(UUID id);
}
