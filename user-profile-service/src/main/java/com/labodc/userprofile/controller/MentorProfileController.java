package com.labodc.userprofile.controller;

import com.labodc.userprofile.entity.profile.MentorProfile;
import com.labodc.userprofile.service.MentorProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/mentor")
public class MentorProfileController {

    private final MentorProfileService service;

    public MentorProfileController(MentorProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MentorProfile> create(@RequestBody MentorProfile body) {
        return ResponseEntity.ok(service.create(body));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MentorProfile> get(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MentorProfile> update(@PathVariable Long userId, @RequestBody MentorProfile body) {
        return ResponseEntity.ok(service.update(userId, body));
    }
}
