package com.labodc.admin_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMentorRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Expertise is required")
    private String expertise;
}
