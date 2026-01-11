package com.labodc.admin_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProjectRequest {

    @NotBlank(message = "Project title is required")
    private String title;

    private String description;

    @NotNull(message = "Enterprise ID is required")
    private Long enterpriseId;

    @Positive(message = "Budget must be positive")
    private BigDecimal budget;
}
