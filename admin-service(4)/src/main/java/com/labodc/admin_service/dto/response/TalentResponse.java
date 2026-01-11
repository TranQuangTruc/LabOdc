package com.labodc.admin_service.dto.response;
import lombok.Data;

@Data
public class TalentResponse {
    private String id;
    private String fullName;
    private String email;
    private String skillSet;
    private String status; // ACTIVE, SUSPENDED
}
