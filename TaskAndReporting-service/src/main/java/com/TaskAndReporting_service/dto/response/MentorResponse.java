package com.TaskAndReporting_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorResponse {
    String id;
    String userId;
    String fullname;
    String status;
}
