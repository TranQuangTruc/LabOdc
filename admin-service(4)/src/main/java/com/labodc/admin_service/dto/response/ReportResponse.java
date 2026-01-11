package com.labodc.admin_service.dto.response;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportResponse {
    private String id;
    private String title;
    private LocalDateTime publishedAt;
}
