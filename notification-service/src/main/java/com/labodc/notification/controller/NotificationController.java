package com.labodc.notification.controller;

import com.labodc.notification.entity.Notification;
import com.labodc.notification.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAll(@RequestParam Long userId) {
        return ResponseEntity.ok(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnread(@RequestParam Long userId) {
        return ResponseEntity.ok(notificationRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markRead(@PathVariable Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        n.setRead(true);
        notificationRepository.save(n);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody CreateRequest req) {
        Notification n = new Notification(req.userId, req.title, req.message, req.type);
        return ResponseEntity.ok(notificationRepository.save(n));
    }

    public static class CreateRequest {
        public Long userId;
        public String title;
        public String message;
        public String type;
    }
}
