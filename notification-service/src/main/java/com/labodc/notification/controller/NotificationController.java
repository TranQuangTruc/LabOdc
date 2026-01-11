package com.labodc.notification.controller;

import com.labodc.notification.dto.CreateNotificationRequest;
import com.labodc.notification.dto.NotificationResponse;
import com.labodc.notification.entity.Notification;
import com.labodc.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification API")
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public Notification create(@RequestBody CreateNotificationRequest request) {
        return service.create(request);
    }

    @GetMapping("/my")
    public List<NotificationResponse> myNotifications(
            @RequestHeader("X-USER-ID") UUID userId
    ) {
        return service.getMyNotifications(userId);
    }

    @GetMapping("/my/unread-count")
    public long unreadCount(
            @RequestHeader("X-USER-ID") UUID userId
    ) {
        return service.unreadCount(userId);
    }

    @PatchMapping("/{id}/read")
    public void markRead(@PathVariable UUID id) {
        service.markAsRead(id);
    }
}
