package com.TaskAndReporting_service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    String id;
    String enterpriseId;
    String title;
    String description;
    float budget;
    String status;
    String approvedBy;
    Instant createAt;
    Instant updateAt;
    String updateBy;
}
