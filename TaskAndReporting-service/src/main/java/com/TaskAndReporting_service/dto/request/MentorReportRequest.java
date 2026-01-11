package com.TaskAndReporting_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorReportRequest {
    String projectId;
    String mentorId;
    String reportType;
    String reportFileUrl;
    String summary;
    String status;
}
