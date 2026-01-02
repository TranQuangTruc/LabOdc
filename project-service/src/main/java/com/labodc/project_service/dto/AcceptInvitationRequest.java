package com.labodc.project_service.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AcceptInvitationRequest {
    private String mentorId;
    private String projectId;
}
