package com.TaskAndReporting_service.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectTaskResponse {
    String id;
    String projectId;
    List<String> talentIds;
    String mentorId;
    String taskCode;
    String taskName;
    String description;
    String priority;
    String status;
    Instant startDate;
    Instant dueDate;
    Instant completionDate;
    Instant createAt;
    Instant UpdateAt;
}
