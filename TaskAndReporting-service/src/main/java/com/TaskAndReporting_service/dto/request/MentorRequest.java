package com.TaskAndReporting_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorRequest {
    String userId;
    String expertise;
    int yearOfExperience;
    String company;
    String CVUrl;
    String bio;
}
