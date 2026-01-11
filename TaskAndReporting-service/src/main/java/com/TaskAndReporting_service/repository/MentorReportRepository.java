package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.MentorReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorReportRepository extends JpaRepository<MentorReport, String> {
    List<MentorReport> findByProjectId(String projectId);
}
