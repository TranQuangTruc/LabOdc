package com.labodc.userprofile.controller;


import com.labodc.userprofile.entity.Notification;
import com.labodc.userprofile.repository.NotificationRepository;
import com.labodc.userprofile.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


private final NotificationRepository repo;
private final UserRepository userRepo;


public NotificationController(NotificationRepository repo, UserRepository userRepo) {
this.repo = repo;
this.userRepo = userRepo;
}


@GetMapping
public ResponseEntity<?> myNotifications(@AuthenticationPrincipal UserDetails user) {
Long userId = userRepo.findByUsername(user.getUsername()).orElseThrow().getId();
return ResponseEntity.ok(repo.findByUserId(userId));
}


@PutMapping("/{id}/read")
public ResponseEntity<?> markRead(@PathVariable Long id) {
Notification n = repo.findById(id).orElseThrow();
n.setRead(true);
repo.save(n);
return ResponseEntity.ok("Read");
}
}