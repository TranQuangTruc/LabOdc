package com.labodc.project_service.dto;

import lombok.Data;

@Data
public class IdeaChangeRequest {
    private String type;
    private String reason;
    private String content;
}