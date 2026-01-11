package com.TaskAndReporting_service.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TalentResponse {
    String id;
    String userId;
    String fullname;
    String skills;
    String summary;
    String LinkedInUrl;
    String GithubUrl;
    String status;
}
