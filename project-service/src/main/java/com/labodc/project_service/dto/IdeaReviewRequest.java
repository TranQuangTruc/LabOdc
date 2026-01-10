package com.labodc.project_service.dto;
import lombok.Data;

@Data
public class IdeaReviewRequest {
    private String status;
    private String note;
}