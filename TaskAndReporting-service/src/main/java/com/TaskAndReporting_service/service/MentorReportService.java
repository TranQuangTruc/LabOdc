package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.MentorReportRequest;
import com.TaskAndReporting_service.dto.response.MentorReportResponse;
import com.TaskAndReporting_service.entity.MentorReport;
import com.TaskAndReporting_service.mapper.MentorReportMapper;
import com.TaskAndReporting_service.repository.MentorReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorReportService {
    MentorReportRepository mentorReportRepository;
    MentorReportMapper mentorReportMapper;

    public MentorReportResponse createReport(MentorReportRequest request){
        MentorReport mentorReport = mentorReportMapper.toMentorReport(request);
        mentorReport.setSubmittedAt(Instant.now());
        return mentorReportMapper.toMentorReportResponse(mentorReportRepository.save(mentorReport));
    }
    public List<MentorReportResponse> getReportByProjectId(String projectId){
        List<MentorReport> byProjectId = mentorReportRepository.findByProjectId(projectId);
        return byProjectId.stream().map(mentorReportMapper::toMentorReportResponse).toList();
    }
}
