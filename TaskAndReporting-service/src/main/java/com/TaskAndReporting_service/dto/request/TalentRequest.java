package com.TaskAndReporting_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TalentRequest {
    String userId;
    String fullname;
    String skills;
    String summary;
    String LinkedInUrl;
    String GithubUrl;
    String status;
}
