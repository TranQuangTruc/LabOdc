package com.labodc.admin_service.dto.response;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProjectResponse {
    private String id;
    private String title;
    private String status; // SUBMITTED, APPROVED, RUNNING, CANCELLED
    private BigDecimal budget;
    private String enterpriseName;
}
