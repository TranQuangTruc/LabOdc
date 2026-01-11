package com.labodc.project_service.dto;

import lombok.Data;

@Data
public class ApplyProjectRequest {
    private String projectId;
    private String talentId;
    private String message;
}
