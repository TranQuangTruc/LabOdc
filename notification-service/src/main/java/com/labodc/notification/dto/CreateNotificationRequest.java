package com.labodc.notification.dto;

import com.labodc.notification.enumtype.NotificationRole;
import com.labodc.notification.enumtype.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateNotificationRequest {

    private UUID userId;
    private NotificationRole role;
    private NotificationType type;
    private String title;
    private String content;
    private String metadata;
}
