package com.TaskAndReporting_service.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterpriseResponse {
    String id;
    String userId;
    String name;
    String phoneNumber;
    String address;
    String website;
    String description;
    String status;
}
