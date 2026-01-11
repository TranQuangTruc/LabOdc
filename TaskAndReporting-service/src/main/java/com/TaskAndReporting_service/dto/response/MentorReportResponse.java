package com.TaskAndReporting_service.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorReportResponse {
    String id;
    String userId;
    String expertise;
    int yearOfExperience;
    String company;
    String CVUrl;
    String bio;
}
