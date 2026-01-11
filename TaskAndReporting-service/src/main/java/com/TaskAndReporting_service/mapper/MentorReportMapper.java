package com.TaskAndReporting_service.mapper;

import com.TaskAndReporting_service.dto.request.MentorReportRequest;
import com.TaskAndReporting_service.dto.response.MentorReportResponse;
import com.TaskAndReporting_service.entity.MentorReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MentorReportMapper {
    MentorReport toMentorReport(MentorReportRequest mentorReportRequest);
    MentorReportResponse toMentorReportResponse(MentorReport mentorReport);
}
