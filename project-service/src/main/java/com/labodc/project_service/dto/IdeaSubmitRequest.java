package com.labodc.project_service.dto;
import lombok.Data;

@Data
public class IdeaSubmitRequest {
    private String title;
    private String description;
    private String enterpriseId;
    private String specialtyId;
}