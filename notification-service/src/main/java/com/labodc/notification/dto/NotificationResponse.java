package com.labodc.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class NotificationResponse {

    private UUID id;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;
}
