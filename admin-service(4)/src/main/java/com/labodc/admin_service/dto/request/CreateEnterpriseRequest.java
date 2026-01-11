package com.labodc.admin_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateEnterpriseRequest {

    @NotBlank(message = "Enterprise name is required")
    private String name;

    @NotBlank(message = "Tax code is required")
    private String taxCode;

    @Email(message = "Email format is invalid")
    private String email;

    @Pattern(
        regexp = "^(0[0-9]{9})$",
        message = "Phone number must be 10 digits and start with 0"
    )
    private String phone;
}
