package com.labodc.admin_service.dto.response;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TemplateResponse {
    private String id;
    private String name;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}
