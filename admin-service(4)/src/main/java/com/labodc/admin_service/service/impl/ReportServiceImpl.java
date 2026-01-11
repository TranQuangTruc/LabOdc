package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.dto.request.PublishReportRequest;
import com.labodc.admin_service.entity.Report;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.repository.ReportRepository;
import com.labodc.admin_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repo;

    @Override
    public Report create(PublishReportRequest request) {
        Report report = new Report();
        report.setTitle(request.getTitle());
        report.setContent(request.getContent());
        report.setPublished(false);
        return repo.save(report); // trả về entity luôn
    }

    @Override
    public List<Report> getAll() {
        return repo.findAll(); // trả về entity list
    }

    @Override
    public List<Report> getPublished() {
        return repo.findByPublishedTrue(); // trả về entity list
    }

    @Override
    public void publish(UUID id) {
        Report report = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        report.setPublished(true);
        repo.save(report);
    }
}
