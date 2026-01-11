package com.labodc.admin_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublishReportRequest {

    @NotBlank(message = "Report title is required")
    private String title;

    @NotBlank(message = "Report content is required")
    private String content;
}
