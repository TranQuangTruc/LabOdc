package com.labodc.admin_service.dto.response;
import lombok.Data;

@Data
public class MentorResponse {
    private String id;
    private String fullName;
    private String expertise;
    private String status; // PENDING, ACTIVE
}
