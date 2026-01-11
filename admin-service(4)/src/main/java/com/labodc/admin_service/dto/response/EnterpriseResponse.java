package com.labodc.admin_service.dto.response;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnterpriseResponse {
    private String id;
    private String name;
    private String email;
    private String status; // PENDING, APPROVED, BLOCKED
    private LocalDateTime createdAt;
}
