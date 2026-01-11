package com.TaskAndReporting_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectTaskRequest {
    String projectId;
    String talentId;
    String mentorId;
    String taskCode;
    String taskName;
    String description;
    String priority;
    String status;
    Instant startDate;
    Instant dueDate;
}
