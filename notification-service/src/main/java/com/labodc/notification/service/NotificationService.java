package com.labodc.notification.service;

import com.labodc.notification.dto.CreateNotificationRequest;
import com.labodc.notification.dto.NotificationResponse;
import com.labodc.notification.entity.Notification;
import com.labodc.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public Notification create(CreateNotificationRequest request) {
        Notification n = Notification.builder()
                .userId(request.getUserId())
                .role(request.getRole())
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .metadata(request.getMetadata())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(n);
    }

    public List<NotificationResponse> getMyNotifications(UUID userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .content(n.getContent())
                        .isRead(n.isRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .toList();
    }

    public long unreadCount(UUID userId) {
        return repository.countByUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(UUID id) {
        Notification n = repository.findById(id).orElseThrow();
        n.setRead(true);
        repository.save(n);
    }
}
